LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := sha1
LOCAL_SRC_FILES := sha1.cpp
LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)