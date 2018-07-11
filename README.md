# Tensorflow Android

## Usage

Clone the repository

`git clone https://github.com/nimbl3/tensorflow-android.git`

- Retrain the model

```
bazel build tensorflow/examples/image_retraining:retrain
bazel-bin/tensorflow/examples/image_retraining/retrain --image_dir harry_potter_datasets/train
```

- Put the model inside the project, here we're using the sample android tensorflow apps

```
# Model directory
tensorflow/examples/assets
```

- Build and run the app

```
bazel build -c opt //tensorflow/examples/android:tensorflow_demo
adb install -r bazel-bin/tensorflow/examples/android/tensorflow_demo.apk
```


## License

This project is Copyright (c) 2014-2018 Nimbl3 Ltd. It is free software,
and may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: /LICENSE

## About

![Nimbl3](https://dtvm7z6brak4y.cloudfront.net/logo/logo-repo-readme.jpg)

This project is maintained and funded by Nimbl3 Ltd.

We love open source and do our part in sharing our work with the community!
See [our other projects][community] or [hire our team][hire] to help build your product.

[community]: https://nimbl3.github.io/
[hire]: https://nimbl3.com/
