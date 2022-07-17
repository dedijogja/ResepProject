
package id.varianresep.iklan;

import java.util.ArrayList;
import java.util.Collection;


class AdPresetCyclingList extends ArrayList<ExpressAdPreset> {

    private int currentIdx = -1;

    AdPresetCyclingList(){
        super();
    }

    public ExpressAdPreset get() {
        if(size() == 0) return null;
        if (size() == 1) return get(0);
        currentIdx = ++currentIdx % size();
        return get(currentIdx);
    }

    @Override
    public boolean add(ExpressAdPreset expressAdPreset) {
        return !(expressAdPreset == null || !expressAdPreset.isValid())
                && super.add(expressAdPreset);
    }

    @Override
    public boolean addAll(Collection<? extends ExpressAdPreset> c) {
        ArrayList<ExpressAdPreset> lst = new ArrayList<>();
        for (ExpressAdPreset eap : c) {
            if(eap!=null && eap.isValid())
                lst.add(eap);
        }
        return super.addAll(lst);
    }
}
