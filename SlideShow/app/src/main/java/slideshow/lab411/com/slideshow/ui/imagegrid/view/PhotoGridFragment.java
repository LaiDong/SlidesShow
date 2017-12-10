package slideshow.lab411.com.slideshow.ui.imagegrid.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import pl.itto.squarelayout.SquareLayout;
import slideshow.lab411.com.slideshow.R;
import slideshow.lab411.com.slideshow.base.BaseFragment;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridView;
import slideshow.lab411.com.slideshow.ui.imagegrid.presenter.PhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.service.RecordingService;
import slideshow.lab411.com.slideshow.ui.showphoto.view.ShowPhotoActivity;
import slideshow.lab411.com.slideshow.ui.widget.PhotoItemDecoration;
import slideshow.lab411.com.slideshow.utils.AppConstants;
import slideshow.lab411.com.slideshow.utils.UiUtils;

/**
 * Created by PL_itto on 12/6/2017.
 */

public class PhotoGridFragment extends BaseFragment implements IPhotoGridView {
    private static final String TAG = "Lab411." + PhotoGridFragment.class.getSimpleName();
    private static final int mImageList[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7
            , R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14, R.drawable.img15,
            R.drawable.img16, R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20};

    /**
     * Init Views
     */
    /* Toolbar */
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    @BindView(R.id.gallery_title)
    TextView mToolbarTitle;


    @BindView(R.id.photo_grid)
    RecyclerView mPhotoGrid;

    IPhotoGridPresenter<IPhotoGridView> mPresenter;
    PhotoGridAdapter mPhotoGridAdapter;
    private boolean mStartRecording = true;
    final Handler mHandler = new Handler();

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
        mPresenter.loadImage(getContext());
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
//        getParentActivity().setSupportActionBar(mToolbar);
//        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(getParentActivity().getTitle());
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        super.onDestroy();
    }

    @Override
    public void updateListPhoto(@NonNull PhotoFolderInfo data) {
        if (mPhotoGridAdapter != null) {
            mPhotoGridAdapter.replaceData(data);
        }
    }

    @Override
    public void onSelectModeSwitch(boolean enabled) {

    }

    @Override
    public void showPhoto(@NonNull List<PhotoInfo> data, int position) {
        Intent intent = new Intent(getContext(), ShowPhotoActivity.class);
        intent.putExtra(AppConstants.ShowPhoto.EXTRA_PHOTO_LIST, (Serializable) data);
        intent.putExtra(AppConstants.ShowPhoto.EXTRA_PHOTO_POSITION, position);
        startActivity(intent);
    }

    class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoHolder> {
        PhotoFolderInfo mPhotoList = null;
        boolean[] mSelectList;
        LinkedHashMap<String, PhotoInfo> mSelectedPhoto;
        boolean mSelectMode = false;

        public PhotoGridAdapter() {
            mPhotoList = new PhotoFolderInfo();
            mSelectList = new boolean[0];
            mSelectMode = false;
            mSelectedPhoto = new LinkedHashMap<>();
        }

        public List<PhotoInfo> getListPhoto() {
            return mPhotoList.getPhotoList();
        }

        public void replaceData(@NonNull PhotoFolderInfo data) {
            mPhotoList = data;
            mSelectList = new boolean[mPhotoList.getPhotoList().size()];
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
            PhotoInfo item = mPhotoList.getPhotoList().get(position);
            if (item != null) {
                holder.bindItem(item);
            }
        }

        @Override
        public int getItemCount() {
            return mPhotoList.getPhotoList().size();
        }

        class PhotoHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.photo_layout)
            SquareLayout mPhotoBgr;

            @BindView(R.id.photo_view)
            ImageView mImg;
            @BindView(R.id.photo_selector)
            CheckBox mSelector;

            public PhotoHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindItem(PhotoInfo item) {
                if (mSelectMode) {
                    mSelector.setVisibility(View.VISIBLE);
                    String imagePath = item.getPhotoPath();
                    if (mSelectedPhoto.containsKey(imagePath))
                        mSelector.setChecked(true);
                    else
                        mSelector.setChecked(false);
                } else {
                    mSelector.setVisibility(View.GONE);
                }
                UiUtils.loadImageFromFile(getContext(), item.getPhotoPath(), mImg);
            }

            @OnClick(R.id.photo_layout)
            void onClick() {
                if (mSelectMode) {
                    PhotoInfo info = mPhotoList.getPhotoList().get(getAdapterPosition());
                    Log.d(TAG, "onClick: " + getAdapterPosition() + " : " + info.getPhotoPath());
                    if (isPhotoSelect(info)) {
                        //deselect
                        unSelectPhoto(info);
                        mSelector.setChecked(false);
                    } else {
                        selectPhoto(info);
                        mSelector.setChecked(true);
                    }
                } else {
                    //Open Image in single activity
                    if (mPhotoList != null) {
                        List<PhotoInfo> list = mPhotoList.getPhotoList();
                        if (list != null && list.size() > 0) {
                            showPhoto(list, getAdapterPosition());
                        }
                    }

                }
            }


            @OnLongClick(R.id.photo_layout)
            boolean onLongClick() {
                if (!mSelectMode) {
                    PhotoInfo info = mPhotoList.getPhotoList().get(getAdapterPosition());
                    mSelectMode = true;
                    selectPhoto(info);
                    notifyDataSetChanged();
                    onSelectModeSwitch(true);
                }
                return true;
            }

            public boolean isPhotoSelect(PhotoInfo info) {
                if (mSelectedPhoto.containsKey(info.getPhotoPath())) return true;
                return false;
            }

            public void unSelectPhoto(PhotoInfo info) {
                mSelectedPhoto.remove(info.getPhotoPath());
            }

            public void selectPhoto(PhotoInfo info) {
                mSelectedPhoto.put(info.getPhotoPath(), info);
            }

        }

        public void clearSelected() {
            mSelectedPhoto.clear();
        }

        public void selectAllPhoto() {
            for (PhotoInfo info : mPhotoList.getPhotoList()) {
                String path = info.getPhotoPath();
                if (!mSelectedPhoto.containsKey(path))
                    mSelectedPhoto.put(path, info);
            }
        }
    }

    @OnClick(R.id.fab_slide_show)
    void onfabClick() {
        //start Slide show here
        //showMessage("We will do this action later");
        onRecord(mStartRecording);

    }


    private void onRecord(boolean start) {
        final Intent intent = new Intent(getActivity(), RecordingService.class);
        File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
        if (!folder.exists()) {
            //folder /SoundRecorder doesn't exist, create the folder
            folder.mkdir();
        }
        getActivity().startService(intent);

    }
}
