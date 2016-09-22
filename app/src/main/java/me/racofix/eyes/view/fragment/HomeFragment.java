package me.racofix.eyes.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.core.adapter.RecyclerAdapter;
import com.android.core.adapter.RecyclerViewHolder;
import com.android.core.base.BaseFragment;
import com.android.core.base.rx.RxView;
import com.android.core.widget.glide.ImageLoader;
import com.android.core.widget.glide.ImageLoaderUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.racofix.eyes.R;
import me.racofix.eyes.model.HomeRepo;
import me.racofix.eyes.presenter.HomeLogicI;
import me.racofix.eyes.presenter.HomeLogicImpl;

/**
 * Author: 码农小阿新
 * Date: 2016/9/18
 * Github: https://github.com/racofix
 */
public class HomeFragment extends BaseFragment implements RxView<HomeRepo> {

    @Bind(R.id.recycler_view_home)
    XRecyclerView mRecylerView;
    private List<HomeRepo.SectionListBean.ItemListBean> home_list = new ArrayList();
    private RecyclerAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitView() {
        mPresenter = getLogicImpl(HomeLogicI.class, this);
        ((HomeLogicImpl) mPresenter).onHomeDataLayer2Api();

        adapter = new RecyclerAdapter<HomeRepo.SectionListBean.ItemListBean>(getActivity(), R.layout.item_content_home, home_list) {
            @Override
            public void convert(RecyclerViewHolder holder, HomeRepo.SectionListBean.ItemListBean repo) {
                holder.setText(R.id.tv_home_title, repo.getData().getTitle());

                ImageLoaderUtil.getInstance().loadImage(getActivity(), new ImageLoader.Builder()
                        .url(repo.getData().getCover().getDetail())
                        .imgView((ImageView) holder.getView(R.id.iv_home_img))
                        .placeHolder(R.color.image_place_normal)
                        .build());
            }
        };
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecylerView.setAdapter(adapter);
    }

    @Override
    public void onReceiveData2Api(HomeRepo repo, boolean b) {
        List<HomeRepo.SectionListBean.ItemListBean> sectionList = repo.getSectionList().get(0).getItemList();
        home_list.addAll(sectionList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
