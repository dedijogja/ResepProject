
package id.varianresep.iklan;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.NativeExpressAdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class AdmobFetcherExpress extends AdmobFetcherBase {

    static final int PREFETCHED_ADS_SIZE = 2;

    private static final int MAX_FETCH_ATTEMPT = 4;

    AdmobFetcherExpress(Context context){
        super();
        mContext = new WeakReference<>(context);
    }

    private List<NativeExpressAdView> mPrefetchedAds = new ArrayList<>();
    private AdPresetCyclingList mAdPresetCyclingList = new AdPresetCyclingList();

    ExpressAdPreset takeNextAdPreset() {
        return this.mAdPresetCyclingList.get();
    }


    void setAdPresets(Collection<ExpressAdPreset> adPresets) {
        mAdPresetCyclingList.clear();
        mAdPresetCyclingList.addAll(adPresets);
    }



    synchronized NativeExpressAdView getAdForIndex(int adPos) {
        if(adPos >= 0 && mPrefetchedAds.size() > adPos)
            return mPrefetchedAds.get(adPos);
        return null;
    }

    synchronized void fetchAd(final NativeExpressAdView adView) {
        if(mFetchFailCount > MAX_FETCH_ATTEMPT)
            return;

        Context context = mContext.get();

        if (context != null) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    adView.loadAd(getAdRequest());
                }
            });
        } else {
            mFetchFailCount++;
        }
    }

    synchronized void setupAd(final NativeExpressAdView adView) {
        if(mFetchFailCount > MAX_FETCH_ATTEMPT)
            return;

        if(!mPrefetchedAds.contains(adView))
            mPrefetchedAds.add(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                onFailedToLoad(adView);
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                onFetched();
            }
        });
    }

    private synchronized void onFetched() {
        mFetchFailCount = 0;
        mNoOfFetchedAds++;
        notifyObserversOfAdSizeChange(mNoOfFetchedAds - 1);
    }

    private synchronized void onFailedToLoad(NativeExpressAdView adView) {
        mFetchFailCount++;
        mNoOfFetchedAds = Math.max(mNoOfFetchedAds - 1, -1);
        mPrefetchedAds.remove(adView);
        notifyObserversOfAdSizeChange(mNoOfFetchedAds - 1);
        ViewParent parent = adView.getParent();
        if(parent!=null && !(parent instanceof RecyclerView
                        || parent instanceof ListView))
            ((View) adView.getParent()).setVisibility(View.GONE);
        else adView.setVisibility(View.GONE);
    }

    @Override
    public synchronized int getFetchingAdsCount() {
        return mPrefetchedAds.size();
    }

    @Override
    public synchronized void destroyAllAds() {
        super.destroyAllAds();
        for(NativeExpressAdView ad:mPrefetchedAds){
            ad.destroy();
        }
        mPrefetchedAds.clear();
    }

    @Override
    public void release() {
        super.release();
        mAdPresetCyclingList.clear();
    }
}
