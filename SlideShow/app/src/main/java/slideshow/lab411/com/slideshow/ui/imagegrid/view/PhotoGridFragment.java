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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.squarelayout.SquareLayout;
import slideshow.lab411.com.slideshow.R;
import slideshow.lab411.com.slideshow.base.BaseFragment;
import slideshow.lab411.com.slideshow.data.model.PhotoFolderInfo;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.IPhotoGridContract.IPhotoGridView;
import slideshow.lab411.com.slideshow.ui.imagegrid.presenter.PhotoGridPresenter;
import slideshow.lab411.com.slideshow.ui.imagegrid.service.RecordingService;
import slideshow.lab411.com.slideshow.ui.widget.PhotoItemDecoration;
import slideshow.lab411.com.slideshow.utils.MediaUtils;
import slideshow.lab411.com.slideshow.utils.UiUtils;

/**
 * Created by PL_itto on 12/6/2017.
 */

public class PhotoGridFragment extends BaseFragment implements IPhotoGridView {
    private static final String TAG = "Lab411." + PhotoGridFragment.class.getSimpleName();
    private static final int mImageList[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7
            , R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14, R.drawable.img15,
            R.drawable.img16, R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20};
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
        getParentActivity().setSupportActionBar(mToolbar);
//        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoHolder> {
        PhotoFolderInfo mPhotoList = null;

        public PhotoGridAdapter() {
            mPhotoList = new PhotoFolderInfo();
        }

        public void replaceData(PhotoFolderInfo data) {
            mPhotoList = data;
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

            public PhotoHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindItem(PhotoInfo item) {
                UiUtils.loadImageFromFile(getContext(), item.getPhotoPath(), mImg);
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
