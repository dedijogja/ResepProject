
package id.varianresep.iklan;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

import id.varianresep.R;

public class WrapAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements AdmobFetcherBase.AdmobListener {

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                for(int i = 0; i<itemCount; itemCount++)
                    notifyItemMoved(fromPosition+i, toPosition+i);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

        });
        
        notifyDataSetChanged();
    }

    private AdmobFetcherExpress adFetcher;
    private Context mContext;
    private AdmobAdapterCalculator AdapterCalculator = new AdmobAdapterCalculator();
    private AdmobAdapterCalculator getAdapterCalculator(){return AdapterCalculator;}

    private static final int VIEW_TYPE_AD_EXPRESS = 0;
    private final static int DEFAULT_NO_OF_DATA_BETWEEN_ADS = 10;
    private final static int DEFAULT_LIMIT_OF_ADS = 3;
    private final static int DEFAULT_VIEWTYPE_SOURCE_MAX = 0;

    private int getViewTypeAdExpress(){
        return getViewTypeBiggestSource() + VIEW_TYPE_AD_EXPRESS + 1;
    }

    public void setNoOfDataBetweenAds(int mNoOfDataBetweenAds) {
        AdapterCalculator.setNoOfDataBetweenAds(mNoOfDataBetweenAds);
    }
    public void setFirstAdIndex(int firstAdIndex) {
        AdapterCalculator.setFirstAdIndex(firstAdIndex);
    }


    public void setLimitOfAds(int mLimitOfAds) {
        AdapterCalculator.setLimitOfAds(mLimitOfAds);
    }

    private int viewTypeBiggestSource;
    private int getViewTypeBiggestSource() {
        return viewTypeBiggestSource;
    }


    private void setViewTypeBiggestSource(int viewTypeBiggestSource) {
        this.viewTypeBiggestSource = viewTypeBiggestSource;
    }

    private WrapAdapter(Context context, String admobReleaseUnitId, String[] testDevicesId, AdSize adSize) {
        Collection<ExpressAdPreset> releaseUnitIds = Collections.singletonList(
                new ExpressAdPreset(admobReleaseUnitId, adSize));
        init(context, releaseUnitIds, testDevicesId);
    }



    public WrapAdapter(Context context, String admobReleaseUnitId, AdSize adSize) {
        this(context, admobReleaseUnitId, null, adSize);
    }

    private void init(Context context, Collection<ExpressAdPreset> expressAdPresets, String[] testDevicesId) {
        setViewTypeBiggestSource(DEFAULT_VIEWTYPE_SOURCE_MAX);
        setNoOfDataBetweenAds(DEFAULT_NO_OF_DATA_BETWEEN_ADS);
        setLimitOfAds(DEFAULT_LIMIT_OF_ADS);
        mContext = context;
        adFetcher = new AdmobFetcherExpress(mContext);
        if(testDevicesId!=null)
            for (String testId: testDevicesId)
                adFetcher.addTestDeviceId(testId);
        adFetcher.addListener(this);
        adFetcher.setAdPresets(expressAdPresets);

        prefetchAds(AdmobFetcherExpress.PREFETCHED_ADS_SIZE);
    }

    private NativeExpressAdView prefetchAds(int cntToPrefetch){
        NativeExpressAdView last = null;
        for (int i = 0; i < cntToPrefetch; i++) {
            final NativeExpressAdView item = AdViewHelper.getExpressAdView(mContext, adFetcher.takeNextAdPreset());
            adFetcher.setupAd(item);
            new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    adFetcher.fetchAd(item);
                }
            }, 50 * i);
            last = item;
        }
        return last;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder == null)
            return;
        if(viewHolder.getItemViewType() == getViewTypeAdExpress()) {
            NativeHolder nativeExpressHolder =
                    (NativeHolder) viewHolder;
            ViewGroup wrapper = nativeExpressHolder.getAdViewWrapper();
            int adPos = AdapterCalculator.getAdIndex(position);
            NativeExpressAdView adView = adFetcher.getAdForIndex(adPos);
            if (adView == null)
                adView = prefetchAds(1);
            recycleAdViewWrapper(wrapper);
            if (adView.getParent() != null)
                ((ViewGroup) adView.getParent()).removeView(adView);
            addAdViewToWrapper(wrapper, adView);

        } else {
            int origPos = AdapterCalculator.getOriginalContentPosition(position,
                    adFetcher.getFetchingAdsCount(), mAdapter.getItemCount());
            mAdapter.onBindViewHolder(viewHolder, origPos);
        }
    }

    private void addAdViewToWrapper(@NonNull ViewGroup wrapper, @NotNull NativeExpressAdView ad) {
        wrapper.addView(ad);
    }

    private void recycleAdViewWrapper(@NonNull ViewGroup wrapper) {
        for (int i = 0; i < wrapper.getChildCount(); i++) {
            View v = wrapper.getChildAt(i);
            if (v instanceof NativeExpressAdView) {
                wrapper.removeViewAt(i);
                break;
            }
        }
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == getViewTypeAdExpress()) {
            ViewGroup wrapper = getAdViewWrapper(parent);
            return new NativeHolder(wrapper);
        }
        else{
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @NotNull
    private ViewGroup getAdViewWrapper(ViewGroup parent) {
        return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_container,
                parent, false);
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            int noOfAds = AdapterCalculator.getAdsCountToPublish(adFetcher.getFetchingAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemCount() > 0 ? mAdapter.getItemCount() + noOfAds : 0;
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        checkNeedFetchAd(position);
        if (AdapterCalculator.canShowAdAtPosition(position, adFetcher.getFetchingAdsCount())) {
            return getViewTypeAdExpress();
        } else {
            int origPos = AdapterCalculator.getOriginalContentPosition(position,
                    adFetcher.getFetchingAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemViewType(origPos);
        }
    }

    private void checkNeedFetchAd(int position){
        if(AdapterCalculator.hasToFetchAd(position, adFetcher.getFetchingAdsCount()))
            prefetchAds(1);
    }

    public void release(){
        adFetcher.release();
    }

    @Override
    public void onAdChanged(int adIdx) {
        int pos = getAdapterCalculator().translateAdToWrapperPosition(adIdx);
        notifyItemChanged(pos==0? 1: Math.max(0, pos-1));
    }

    @Override
    public void onAdChanged() {
        notifyDataSetChanged();
    }

}
