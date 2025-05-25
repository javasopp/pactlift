# PactLift - 习惯养成打卡应用

PactLift是一款简洁高效的Android习惯养成打卡应用，帮助用户培养良好习惯，提高自律性。

## 主要功能

1. **习惯管理**
   - 创建新习惯，设置名称、描述和目标天数
   - 激活/暂停习惯
   - 习惯进度可视化显示

2. **每日打卡**
   - 简单快捷的打卡界面
   - 大型居中打卡按钮，操作直观
   - 显示当前时间和打卡状态

3. **打卡记录**
   - 查看历史打卡记录
   - 按时间排序
   - 仅显示活跃习惯的记录

## 技术实现

### 架构

应用采用简化的MVC架构：

- **Model**: 数据模型层，包含`Habit`和`CheckIn`数据类
- **View**: UI层，使用Fragment展示不同功能界面
- **Controller**: 使用`DataManager`管理数据操作

### 使用的框架与库

- **Android核心组件**
  - `androidx.appcompat`: 提供向后兼容性支持
  - `androidx.recyclerview`: 实现列表显示
  - `androidx.cardview`: 实现卡片式UI
  - `androidx.constraintlayout`: 实现灵活的UI布局

- **Material Design组件**
  - `com.google.android.material`: 提供现代化的UI控件
  - 包括FloatingActionButton、CardView等组件

- **数据存储**
  - 使用SharedPreferences + Gson序列化存储数据
  - 不依赖复杂的数据库设计，轻量级实现

## 设计特点

1. **简洁直观的用户界面**
   - 三个主要功能标签页：习惯、打卡、记录
   - 大型打卡按钮，提升用户体验
   - 现代化的Material Design风格

2. **轻量级实现**
   - 无需复杂数据库
   - 启动速度快，资源占用少

3. **灵活的习惯管理**
   - 可随时激活或暂停习惯
   - 默认30天目标，符合习惯养成周期

## 代码结构

```
com.sopp.pactlift/
├── data/
│   └── DataManager.kt       # 数据管理类
├── model/
│   ├── Habit.kt             # 习惯数据模型
│   └── CheckIn.kt           # 打卡记录数据模型
├── ui/
│   ├── HabitFragment.kt     # 习惯管理界面
│   ├── CheckInFragment.kt   # 打卡界面
│   └── RecordsFragment.kt   # 记录查看界面
└── MainActivity.kt          # 主活动
```

## 构建与运行

1. 克隆仓库
2. 在Android Studio中打开项目
3. 构建并运行应用

## 版本信息

- 当前版本: 1.0
- 最低支持Android版本: 5.0 (API 21)
- 目标Android版本: 11 (API 30) 