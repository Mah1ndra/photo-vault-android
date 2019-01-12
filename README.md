# Calculator - Photo Vault
A photo vault app that disguises as a calculator. Developed using Dagger 2, Butterknife, Glide. 
The coding architecture follows the MVP pattern.

#   App Features
- Features a simple calculator interface that also acts the keypad to enter the PIN code.
- Opens the photo vault when the PIN code and the selected activation button is pressed.
- A 256 bit secure secret encryption key is generated (using the PBKDF2 algorithm)
 based on the PIN code.
- Files are encrypted/decrypted using AES in GCM Mode.
 