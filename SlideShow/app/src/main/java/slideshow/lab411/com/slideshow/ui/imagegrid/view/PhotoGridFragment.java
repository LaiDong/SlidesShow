package slideshow.lab411.com.slideshow.ui.imagegrid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.squarelayout.SquareLayout;
import slideshow.lab411.com.slideshow.R;
import slideshow.lab411.com.slideshow.base.BaseFragment;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridView;
import slideshow.lab411.com.slideshow.ui.imagegrid.presenter.PhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.service.RecordingService;
import slideshow.lab411.com.slideshow.ui.selectphoto.view.PhotoSelectActivity;
import slideshow.lab411.com.slideshow.ui.showphoto.view.ShowPhotoActivity;
import slideshow.lab411.com.slideshow.ui.widget.PhotoItemDecoration;
import slideshow.lab411.com.slideshow.utils.AppConstants;
import slideshow.lab411.com.slideshow.utils.AppConstants.ShowPhoto;
import slideshow.lab411.com.slideshow.utils.UiUtils;

/**
 * Created by PL_itto on 12/6/2017.
 */

public class PhotoGridFragment extends BaseFragment implements IPhotoGridView {
    private static final String TAG = "Lab411." + PhotoGridFragment.class.getSimpleName();

    /**
     * Init Views
     */
    /* Toolbar */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    RelativeLayout mToolbar;
//    @BindView(R.id.gallery_title)
//    TextView mToolbarTitle;


    @BindView(R.id.photo_grid)
    RecyclerView mPhotoGrid;

    IPhotoGridPresenter<IPhotoGridView> mPresenter;
    PhotoGridAdapter mPhotoGridAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new PhotoGridPresenter<>();
        mPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_photo_grid, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        setup();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null)
            mPresenter.initDefaultPhoto();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.photo_grid_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_photo:
                openGallery();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    void setup() {
        /* Photo Grid */
        int span = getResources().getInteger(R.integer.photo_grid_span);
        mPhotoGrid.setLayoutManager(new GridLayoutManager(getContext(), span, LinearLayoutManager.VERTICAL, false));
        mPhotoGrid.addItemDecoration(new PhotoItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_spacing),
                span));
        mPhotoGridAdapter = new PhotoGridAdapter();
        mPhotoGrid.setAdapter(mPhotoGridAdapter);
        setupActionBar();
    }

    void setupActionBar() {
        getParentActivity().setSupportActionBar(mToolbar);
//        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarTitle.setText(getParentActivity().getTitle());
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }

    @Override
    public void updateListPhoto(@NonNull List<PhotoInfo> data) {
        if (mPhotoGridAdapter != null) {
            Log.d(TAG, "updateListPhoto: "+data.size());
            mPhotoGridAdapter.replaceData(data);
        }
    }

    @Override
    public void onSelectModeSwitch(boolean enabled) {

    }

    @Override
    public void showPhoto(@NonNull List<PhotoInfo> data, int position) {
        Intent intent = new Intent(getContext(), ShowPhotoActivity.class);
        intent.putExtra(ShowPhoto.EXTRA_PHOTO_LIST, (Serializable) data);
        intent.putExtra(ShowPhoto.EXTRA_PHOTO_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void openGallery() {
        Intent intent = new Intent(getContext(), PhotoSelectActivity.class);
        startActivityForResult(intent, AppConstants.PhotoGrid.REQUEST_PHOTO);
    }

    @Override
    public void openSlideShow() {
        if (mPhotoGridAdapter != null) {
            List<PhotoInfo> dataList = mPhotoGridAdapter.getDataList();
            if (dataList != null && dataList.size() > 0) {
                Intent intent = new Intent(getContext(), ShowPhotoActivity.class);
                intent.putExtra(ShowPhoto.EXTRA_MODE_SHOW, ShowPhoto.MODE_SLIDE);
                intent.putExtra(ShowPhoto.EXTRA_PHOTO_LIST, (Serializable) dataList);
                startActivity(intent);
            } else {
//                showMessage();
            }

        }
    }

    class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoHolder> {
        List<PhotoInfo> mDataList;
        boolean mSelectMode = false;

        public PhotoGridAdapter() {
            mDataList = new ArrayList<>();
            mSelectMode = false;
        }


        public void replaceData(@NonNull List<PhotoInfo> data) {
            mDataList = data;
            notifyDataSetChanged();
        }

        public void addData(List<PhotoInfo> list) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =
                    LayoutInflater.from(getContext()).inflate(R.layout.photo_grid_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            PhotoInfo item = mDataList.get(position);
            if (item != null) {
                holder.bindItem(item);
            }
        }

        @Override
        public int getItemCount() {
            int count = mDataList.size();
            Log.d(TAG, "getItemCount: " + count);
            return count;
        }

        public List<PhotoInfo> getDataList() {
            return mDataList;
        }

        class PhotoHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.photo_layout)
            SquareLayout mPhotoBgr;

            @BindView(R.id.photo_view)
            ImageView mImg;

            public PhotoHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindItem(PhotoInfo item) {
                Log.d(TAG, "bindItem: "+getAdapterPosition());
                if (item.isResImage()) {
                    UiUtils.loadImageRes(getContext(), item.getResImageId(), mImg);
                } else
                    UiUtils.loadImageFromFile(getContext(), item.getPhotoPath(), mImg);
            }

            @OnClick(R.id.photo_layout)
            void onClick() {
                if (mSelectMode) {
                    PhotoInfo info = mDataList.get(getAdapterPosition());
                } else {
                    //Open Image in single activity
                    if (mDataList != null && mDataList.size() > 0) {
                        showPhoto(mDataList, getAdapterPosition());
                    }

                }
            }


        }
    }

    @OnClick(R.id.fab_slide_show)
    void onfabClick() {
        //start Slide show here
        //showMessage("We will do this action later");
        openSlideShow();
        onRecord();
    }


    private void onRecord() {
        final Intent intent = new Intent(getActivity(), RecordingService.class);
        File folder = new File(Environment.getExternalStorageDirectory() + "/SlideShow");
        if (!folder.exists()) {
            //folder /SlideShow doesn't exist, create the folder
            folder.mkdir();
        }
        getActivity().startService(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.PhotoGrid.REQUEST_PHOTO:
                    List<PhotoInfo> list = (List<PhotoInfo>) data.getSerializableExtra(AppConstants.PhotoSelect.EXTRA_PHOTO_RESULT);
                    if (list != null && list.size() > 0 && mPhotoGridAdapter != null) {
                        mPhotoGridAdapter.addData(list);
                    }
                    break;
            }
        }
    }
}
