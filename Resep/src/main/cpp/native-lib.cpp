#include <jni.h>
#include <string>


std::string keyDesText = "?Kv,6(nKR|V;c<5t";
std::string keyDesAssets = "H9bM3HS9jF0hdQTQiwTawA==";

std::string packageName = "id.varianresep.resepsotong";

std::string adInterstitial =  "nSWDPPe]o,qQ()_++89@5555$%&36Q_)&-=7(+";
std::string adNativeList =    "nSWDPPe]o,qQ()_++89@5555$%&39++$^88q()";
std::string adNativeMenu =    "nSWDPPe]o,qQ()_++89@5555$%&39Q^$@88-((";
std::string adNativePembuka = "nSWDPPe]o,qQ()_++89@5555$%&36_#!%-0-(_";
std::string startAppId = "5_*&#%77=";

std::string smesek = "JSIDL`Uyy]y6";

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_keyDesText(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(keyDesText.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_keyDesAssets(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(keyDesAssets.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_packageName(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(packageName.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_adInterstitial(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adInterstitial.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_adNativeList(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adNativeList.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_adNativeMenu(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adNativeMenu.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_adNativePembuka(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adNativePembuka.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_startAppId(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(startAppId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_varianresep_bantu_KontakNative_smesek(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(smesek.c_str());
}