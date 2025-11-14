cmake_minimum_required(VERSION 3.18.1)

project("edgedetection")

# If OpenCV is present in the project, configure OpenCV_DIR accordingly
set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../../../opencv/sdk/native/jni)
find_package(OpenCV REQUIRED QUIET)

add_library(native-lib SHARED native-lib.cpp)

include_directories(${OpenCV_INCLUDE_DIRS})

find_library(log-lib log)

target_link_libraries(native-lib ${OpenCV_LIBS} ${log-lib})
