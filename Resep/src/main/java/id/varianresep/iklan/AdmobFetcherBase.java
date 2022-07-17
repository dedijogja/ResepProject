
package id.varianresep.iklan;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

abstract class AdmobFetcherBase {

    private List<AdmobListener> mAdNativeListeners = new ArrayList<>();
    int mNoOfFetchedAds;
    int mFetchFailCount;
    WeakReference<Context> mContext = new WeakReference<>(null);

    private ArrayList<String> testDeviceId = new ArrayList<>();
    private ArrayList<String> getTestDeviceIds() {
        return testDeviceId;
    }
    void addTestDeviceId(String testDeviceId) {
        this.testDeviceId.add(testDeviceId);
    }

    synchronized void addListener(AdmobListener listener) {
        mAdNativeListeners.add(listener);
    }

    public abstract int getFetchingAdsCount();

    public synchronized void destroyAllAds() {
        mFetchFailCount = 0;
        mNoOfFetchedAds = 0;
        notifyObserversOfAdSizeChange(-1);
    }

    public void release(){
        destroyAllAds();
        mContext.clear();
    }


    void notifyObserversOfAdSizeChange(final int adIdx) {
        final Context context = mContext.get();
        if(context != null) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (AdmobListener listener : mAdNativeListeners)
                        if(adIdx < 0)
                            listener.onAdChanged();
                        else listener.onAdChanged(adIdx);
                }
            });
        }
    }


    synchronized AdRequest getAdRequest() {
        AdRequest.Builder adBldr = new AdRequest.Builder();
        for (String id : getTestDeviceIds()) {
            adBldr.addTestDevice(id);
        }
        return adBldr.build();
    }

    interface AdmobListener {
        void onAdChanged(int adIdx);
        void onAdChanged();
    }
}
