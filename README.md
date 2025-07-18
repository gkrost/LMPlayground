
<h1 align="center">LM Playground</h1>

<p align="center">
<img src="art/logo.png"/>
</p>

LM Playground is designed as a universal platform for experimenting with various types of Large Language Models (LLMs) on Android device. It allows users to download different LLMs, load them onto the application, and converse with these models.

![preview](art/Preview.png)

## Currently supported models
* Qwen2.5 0.5B
* Qwen2.5 1.5B
* Llama3.2 1B
* Llama3.2 3B
* Google Gemma2 2B
* Microsoft Phi3.5 mini
* Llama3.1 7B
* Mistral 7B
* Google Gemma2 9B

## Install
If you're just looking to install LM Playground, you can find it on Google Play. If you're a developer wanting to contribute, read on.

## Build Instructions
Prerequisites:
* Android Studio: [Latest](https://developer.android.com/studio/releases)
* NDK: 27.2.12479018+
* Make sure the Android SDK is installed and set in `local.properties`:
 ```
  sdk.dir=/path/to/Android/sdk
  ```
  Replace `/path/to/Android/sdk` with the location of your SDK.
  Use the command line tools to install the required NDK and accept the
  licenses:
  ```
  sdkmanager "ndk;27.2.12479018" --licenses
  ```

1. Clone the repository:
```
git clone https://github.com/gkrost/LMPlayground.git
```
2. Open the project in Android Studio: `File` > `Open` > Select the cloned repository.
3. Connect an Android device or start an emulator.
4. Run the application using `Run` > `Run 'app'` or the play button in Android Studio.

# License
This project is licensed under the [MIT License](LICENSE).

# Acknowledgments
This project was built based on [llama.cpp](https://github.com/ggerganov/llama.cpp) project. The application uses GGUF-format models with Q4KM quantization from [Hugging Face](https://huggingface.co/).

# Contact
If you have any questions, suggestions, or issues, feel free to open an issue.


