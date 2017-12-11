package slideshow.lab411.com.slideshow.ui.showphoto.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import slideshow.lab411.com.slideshow.R;
import slideshow.lab411.com.slideshow.base.BaseFragment;
import slideshow.lab411.com.slideshow.data.model.PhotoInfo;
import slideshow.lab411.com.slideshow.ui.imagegrid.service.RecordingService;
import slideshow.lab411.com.slideshow.ui.showphoto.IShowPhotoContract.IShowPhotoPresenter;
import slideshow.lab411.com.slideshow.ui.showphoto.IShowPhotoContract.IShowPhotoView;
import slideshow.lab411.com.slideshow.ui.showphoto.presenter.ShowPhotoPresenter;
import slideshow.lab411.com.slideshow.utils.UiUtils;

/**
 * Created by PL_itto on 12/10/2017.
 */

public class ShowPhotoFragment extends BaseFragment implements IShowPhotoView {
    private static final String TAG = "Lab411." + ShowPhotoFragment.class.getSimpleName();
    IShowPhotoPresenter<IShowPhotoView> mPresenter;
    private List<PhotoInfo> mPhotoInfoList = null;
    private int mCurrentPosition = -1;


    /* Views */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_pager)
    ViewPager mPhotoPager;

    MenuItem mSlideShowItem;
    ShowPhotoAdapter mAdapter;
    Timer mTimer = null;
    TimerTask mSlideShowTask = null;

    public static ShowPhotoFragment newInstance(List<PhotoInfo> data, int pos) {
        ShowPhotoFragment fragment = new ShowPhotoFragment();
        fragment.mPhotoInfoList = data;
        fragment.mCurrentPosition = pos;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setupData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_show_photo, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        setupActionBar();
        setupView();
        return view;
    }

    void setupData() {
        mPresenter = new ShowPhotoPresenter<>();
        mPresenter.onAttach(this);
        mAdapter = new ShowPhotoAdapter(mPhotoInfoList);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mCurrentPosition != -1) {
            mPhotoPager.setCurrentItem(mCurrentPosition);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        mSlideShowItem = menu.findItem(R.id.action_slide);
    }

    void setupView() {
        if (mAdapter == null)
            mAdapter = new ShowPhotoAdapter(mPhotoInfoList);
        mPhotoPager.setAdapter(mAdapter);
        mPhotoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean isFirst = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isFirst && position == 0 && positionOffset == 0) {
                    onPageSelected(0);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mAdapter != null) {
                    PhotoInfo info = mAdapter.getItem(position);
                    if (info != null) {
                        updateCurrentItem(info);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setupActionBar() {
        getParentActivity().setSupportActionBar(mToolbar);
        getParentActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDetach();
        stopSlideShow();
        super.onDestroy();
    }

    @Override
    public void updateCurrentItem(PhotoInfo info) {
        mToolbar.setTitle(info.getDateTaken());
        mToolbar.setSubtitle(info.getTimeTaken());
    }

    @Override
    public void startSlideShow() {
        if (mAdapter != null && mAdapter.getCount() > 0) {
            mSlideShowItem.setIcon(R.drawable.ic_stop);
            mTimer = new Timer();
            mSlideShowTask = new TimerTask() {
                @Override
                public void run() {
                    getParentActivity().runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            if (mAdapter != null) {
                                int count = mAdapter.getCount();
                                if (count > 0) {
                                    int currentPosition = mPhotoPager.getCurrentItem();
                                    if (currentPosition < count - 1) {
                                        currentPosition++;
                                    } else {
                                        currentPosition = 0;
                                    }
                                    mPhotoPager.setCurrentItem(currentPosition);
                                }
                            }
                        }
                    });

                }
            };
            mTimer.scheduleAtFixedRate(mSlideShowTask, 5*1000, 2000);
        }
    }

    @Override
    public void stopSlideShow() {
        if (mSlideShowTask != null) {
            mSlideShowItem.setIcon(R.drawable.ic_slideshow_dark);
            mSlideShowTask.cancel();
            mSlideShowTask = null;
        }
    }

    class ShowPhotoAdapter extends PagerAdapter {
        List<PhotoInfo> mList;
        LayoutInflater mInflater;

        public ShowPhotoAdapter(List<PhotoInfo> data) {
            mList = data;
            mInflater = LayoutInflater.from(getContext());
        }

        public PhotoInfo getItem(int pos) {
            if (mList != null && mList.size() > pos) {
                return mList.get(pos);
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d(TAG, "instantiateItem: " + position);
            View view = mInflater.inflate(R.layout.show_photo_item, container, false);
            ImageView img = view.findViewById(R.id.photo_item);
            PhotoInfo info = mList.get(position);
            UiUtils.loadImageFromFile(getContext(), info.getPhotoPath(), img);
            container.addView(view, 0);
            return view;
        }

        @Override
        public int getCount() {
            if (mList != null)
                return mList.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getParentActivity().onBackPressed();
                return true;
            case R.id.action_slide:
                if (mSlideShowTask != null) {
                    stopSlideShow();
                    onRecord(false);
                } else {
                    startSlideShow();
                    onRecord(true);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRecord(boolean start) {
        final Intent intent = new Intent(getActivity(), RecordingService.class);
        if(start){
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }
            getActivity().startService(intent);
        }else{
            getActivity().stopService(intent);
        }
    }
}
