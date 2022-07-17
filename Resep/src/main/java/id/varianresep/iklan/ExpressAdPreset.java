
package id.varianresep.iklan;


import android.text.TextUtils;

import com.google.android.gms.ads.AdSize;

import java.util.Locale;

class ExpressAdPreset {
    private static final AdSize SIZE_DEFAULT_EXPRESS = new AdSize(AdSize.FULL_WIDTH, 150);

    private String adUnitId;
    private AdSize adSize;

    private ExpressAdPreset(){
        this.adSize = SIZE_DEFAULT_EXPRESS;
    }

    private ExpressAdPreset(String adUnitId){
        this();
        if(!TextUtils.isEmpty(adUnitId))
            this.adUnitId = adUnitId;
    }

    ExpressAdPreset(String adUnitId, AdSize adSize){
        this(adUnitId);
        if(adSize != null)
            this.adSize = adSize;
    }

    String getAdUnitId(){
        return this.adUnitId;
    }


    AdSize getAdSize(){
        return this.adSize;
    }


    boolean isValid(){
        return !TextUtils.isEmpty(this.adUnitId);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExpressAdPreset) {
            ExpressAdPreset other = (ExpressAdPreset) o;
            return (this.adUnitId.equals(other.adUnitId)
                    && this.adSize.getHeight() == other.adSize.getHeight()
                    && this.adSize.getWidth() == other.adSize.getWidth());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + adUnitId.hashCode();
        hash = hash * 31 + adSize.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s | %s", adUnitId, adSize);
    }
}
