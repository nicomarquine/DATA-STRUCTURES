# set cmake minimum required version
cmake_minimum_required(VERSION 3.25 FATAL_ERROR)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# define a c project with name provided as parameter (cmake CMakeLists.txt -DPRJCT_NAME=foo)
project(data_structures_c C)

# use c17 standard
set(CMAKE_C_STANDARD 17)

# enable compilation warnings
add_compile_options(-Wall -Wextra -Wpedantic)

# set src folder as root for includes
include_directories(src)

# get all *.c files recursively in src folder and subfolders
file(GLOB_RECURSE SRC_FILES src/*.c)

# get all *.h files recursively in src folder and subfolders
file(GLOB_RECURSE HDR_FILES src/*.h)

# add all those sources to executable
add_executable(${PROJECT_NAME} ${SRC_FILES} ${HDR_FILES})
