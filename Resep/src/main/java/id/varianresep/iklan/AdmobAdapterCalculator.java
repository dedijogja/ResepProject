

package id.varianresep.iklan;

class AdmobAdapterCalculator {

    private int mNoOfDataBetweenAds;

    private int getNoOfDataBetweenAds() {
        return mNoOfDataBetweenAds;
    }

    void setNoOfDataBetweenAds(int mNoOfDataBetweenAds) {
        this.mNoOfDataBetweenAds = mNoOfDataBetweenAds;
    }

    private int firstAdIndex = 0;

    private int getFirstAdIndex() {
        return firstAdIndex;
    }

    void setFirstAdIndex(int firstAdIndex) {
        this.firstAdIndex = firstAdIndex;
    }

    private int mLimitOfAds;

    private int getLimitOfAds() {
        return mLimitOfAds;
    }

    void setLimitOfAds(int mLimitOfAds) {
        this.mLimitOfAds = mLimitOfAds;
    }

    int getAdsCountToPublish(int fetchedAdsCount, int sourceItemsCount){
        if(fetchedAdsCount <= 0 || getNoOfDataBetweenAds() <= 0) return 0;
        int expected = 0;
        if(sourceItemsCount > 0 && sourceItemsCount >= getOffsetValue()+1)
            expected = (sourceItemsCount - getOffsetValue()) / getNoOfDataBetweenAds() + 1;
        expected = Math.max(0, expected);
        expected = Math.min(fetchedAdsCount, expected);
        return Math.min(expected, getLimitOfAds());
    }

    int getOriginalContentPosition(int position, int fetchedAdsCount, int sourceItemsCount) {
        int noOfAds = getAdsCountToPublish(fetchedAdsCount, sourceItemsCount);
        int adSpacesCount = (getAdIndex(position) + 1);
        return position - Math.min(adSpacesCount, noOfAds);
    }

    int translateAdToWrapperPosition(int adPos) {
        return adPos*(getNoOfDataBetweenAds() + 1) + getOffsetValue();
    }


    boolean canShowAdAtPosition(int position, int fetchedAdsCount) {
        return isAdPosition(position) && isAdAvailable(position, fetchedAdsCount);
    }


    int getAdIndex(int position) {
        int index = -1;
        if(position >= getOffsetValue())
            index = (position - getOffsetValue()) / (getNoOfDataBetweenAds()+1);
        return index;
    }

    private boolean isAdPosition(int position) {
        int result = (position - getOffsetValue()) % (getNoOfDataBetweenAds() + 1);
        return result == 0;
    }

    private int getOffsetValue() {
        return getFirstAdIndex() > 0 ? getFirstAdIndex() : 0;
    }

    private boolean isAdAvailable(int position, int fetchedAdsCount) {
        if(fetchedAdsCount == 0) return false;
        int adIndex = getAdIndex(position);
        int firstAdPos = getOffsetValue();

        return position >= firstAdPos && adIndex >= 0 && adIndex < getLimitOfAds() && adIndex < fetchedAdsCount;
    }

    boolean hasToFetchAd(int position, int fetchingAdsCount){
        int adIndex = getAdIndex(position);
        int firstAdPos = getOffsetValue();
        return  position >= firstAdPos && adIndex >= 0 && adIndex < getLimitOfAds() && adIndex >= fetchingAdsCount;
    }
}
