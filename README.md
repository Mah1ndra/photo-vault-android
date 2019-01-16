# Calculator - Photo Vault
A photo vault app that disguises as a calculator. Developed using Dagger 2, Butterknife, Glide. 
The coding architecture follows the MVP pattern.

# App Features
- A simple calculator interface that also acts as the keypad to enter the vault PIN code.
- Decrypts the encrypted images and opens the photo vault when the PIN code and the selected activation button is pressed.
- Hides photo activity and removes all the temporary decrypted images whenever the app goes in background or device gets locked. Opens the calculator interface when restarted.

# Security Features
- A 256 bit secure encryption key is generated (using the PBKDF2 algorithm) based on the PIN code and a random salt.
- Image files are encrypted/decrypted using AES in GCM Mode
- New Initialization Vector (IV) is generated for each new photo and stored in the file name for later decryption.

# Screenshots
<img src="https://raw.githubusercontent.com/zakir224/photo-vault-android/master/1.png?sanitize=true&raw=true" width="200" height="400" align="left"/>

<img src="https://raw.githubusercontent.com/zakir224/photo-vault-android/master/2.png?sanitize=true&raw=true" width="200" height="400" align="left"/>

<img src="https://raw.githubusercontent.com/zakir224/photo-vault-android/master/3.png?sanitize=true&raw=true" width="200" height="400" align="left"/>

<img src="https://raw.githubusercontent.com/zakir224/photo-vault-android/master/4.png?sanitize=true&raw=true" width="200" height="400" align="left"/>

 
