
package id.varianresep.iklan;

import android.content.Context;
import android.widget.AbsListView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

class AdViewHelper {

    static NativeExpressAdView getExpressAdView(Context context, ExpressAdPreset expressAdPreset) {
        NativeExpressAdView adView = new NativeExpressAdView(context);
        AdSize adSize = expressAdPreset.getAdSize();
        adView.setAdSize(adSize);
        adView.setAdUnitId(expressAdPreset.getAdUnitId());
        adView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                adSize.getHeightInPixels(context)));
        return adView;
    }
}
