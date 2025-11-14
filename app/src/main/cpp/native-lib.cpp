#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <android/log.h>

#define TAG "NativeLib"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

extern "C" JNIEXPORT void JNICALL
Java_com_flamapp_edgedetection_MainActivity_nativeProcessFrame(
        JNIEnv* env,
        jobject thiz,
        jlong matAddr) {

    try {
        cv::Mat& frame = *(cv::Mat*)matAddr;

        cv::Mat gray;
        cv::cvtColor(frame, gray, cv::COLOR_RGBA2GRAY);
        cv::GaussianBlur(gray, gray, cv::Size(5, 5), 1.5);
        cv::Mat edges;
        cv::Canny(gray, edges, 50, 150);
        cv::cvtColor(edges, frame, cv::COLOR_GRAY2RGBA);

        LOGD("Frame processed successfully");
    } catch (const cv::Exception& e) {
        LOGE("OpenCV exception: %s", e.what());
    } catch (...) {
        LOGE("Unknown exception in native code");
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_flamapp_edgedetection_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject thiz) {
    std::string hello = "Edge Detection Native Library";
    return env->NewStringUTF(hello.c_str());
}
