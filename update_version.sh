#!/bin/bash

# 读取当前版本号
GRADLE_FILE="app/build.gradle"
CURRENT_VERSION=$(grep -o "versionName \"[0-9.]*\"" $GRADLE_FILE | grep -o "[0-9.]*")

echo "当前版本号: $CURRENT_VERSION"

# 将版本号加0.01
if [[ "$CURRENT_VERSION" == "0.01" || "$CURRENT_VERSION" == ".02" ]]; then
    # 修复格式错误的版本号
    NEW_VERSION="0.02"
else
    MAJOR=$(echo "$CURRENT_VERSION" | cut -d. -f1)
    MINOR=$(echo "$CURRENT_VERSION" | cut -d. -f2)
    MINOR_NEW=$(echo "$MINOR + 1" | bc)
    NEW_VERSION="${MAJOR}.$(printf "%02d" $MINOR_NEW)"
fi

echo "新版本号: $NEW_VERSION"

# 更新build.gradle文件中的版本号
sed -i '' "s/versionName \"$CURRENT_VERSION\"/versionName \"$NEW_VERSION\"/" $GRADLE_FILE

echo "版本号已更新为 $NEW_VERSION"

# 自动构建新版本
./gradlew clean assembleDebug

# 复制APK到根目录
cp app/build/outputs/apk/debug/app-debug.apk app.apk

echo "新版本APK已生成: app.apk" 