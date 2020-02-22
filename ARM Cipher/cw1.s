@ Module: CMP-5013A Architectures and Operang Systems

@ Assignment: Simple cipher in ARMv-7 assesmbly language.

@ Authors: Jan Escayg (100256606) & Elie Harris (100244410)

@ Update log:

	@ v1. getting len function up and running		[04/11/2019]

	@ v2. getting gcd function up and running		[04/11/2019]

	@ v3. getting clean function up and running 		[04/11/2019]

	@ v4. getting encryption up and running for the 1st key [04/11/2019]

	@ v5. getting selection function up and running		[04/11/2019]

	@ v6. getting decryption function up and running	[04/11/2019]

	@ Currently working on:

		@ getting the encryption function up and working
			@ Current state:
				@ Encryption works but it wont loop for the second key

	@ Still to code:

		@ Taking inputs for the message/key1/key2

.data
.balign 4
message: .asciz "crrjqtd"
key1: .asciz "lock"
key2: .asciz "ccc"
emsg: .asciz "%c"
result: .asciz "\n The string length  is: %d\n"
prompt1: .asciz "\n Do you wish to encrypt(0) or decrypt(1):  "
prompt2: .asciz "\n That number is neither a 0 nor a 1. Please try again. "
scan_pattern: .asciz "%d"
number_read: .word 1

.text
.balign 4
.global main
len:
	@ Function to calculate the length of the string stored in r1 and returns the result to r0
        MOV r0, #0
        lenloop:
                LDRB r2,[r1],#1
                CMP r2,#0               @ checks that the pointer is not pointing at null
                BEQ exit
                ADD r0,r0,#1            @ counter to track the number of letters in the string
                B lenloop

exit:
	@ Function to branch back to the link register
	BX lr                           @ return string length (in r0) to OS

gcd:
	@ Function to find the gcd of r0 and r1, using only subtraction
	CMP r0, r1
	BGT big
	BLT small
	B exit
	big:
		SUB r0,r0, r1
		B gcd
	small:
		SUB r1,r1,r0
		B gcd

clean:
	@ Function to remove whitespace and convert capitals to lowercase from the string in r0

	@ Registers used:		@ r0 = message string
	@				@ r2 = bit by bit of the message string

	cleanLoop:
		LDRB r2, [r0],#1	@ Loads in incrementing bits of the message stored in r0 into r2
		CMP r2, #0		@ Checks if the string is empty and if it is we exit the function as its complete
		BEQ exit
		CMP r2, #97		@ If the value is greater than ascii 97 'a' it is either already lowercase or it's a symbol
		BGE cleanLoopGT
		B capitalFinder		@ If the value isn't greater than ascci 97 'a' it could be either a capital or a symbol

	cleanLoopGT:			@ This function checks if it is a lowercase char as if its less than ASCII 'z'
		CMP r2, #122
		BLE encrypt		@ We indentify that it's already a lowercase char
		@ we need to then branch to encryption with this lowercase char
		B cleanLoop		@ If it wasnt less than 'z' it must be a symbol then we branch to the cleaner to remove the value

	capitalFinder:			@ If a value is in this function we know it is less than 'a' therefore it is a uppercase or a symbol
		CMP r2, #65
                BGE editCapitalGT	@ If the value is greater than 'A' it is either a symbol or an uppercase char
		B cleanLoop		@ This removes the symbols lower than ASCII 'A'

	editCapitalGT:			@ If a value is in this function we know it must be between ASCII 'A'(65) and '`'(96)
		CMP r2, #90
                BLE editCapital		@ If the value is less than 90 we know the value is uppercase and we need to convert it to lowercase
		B cleanLoop		@ Else we will just move onto the next char in the string

	editCapital:
		@ Function will be hit if a capital char was entered and it will convert it to a lower case char
		ADD r2, r2, #32		@ Shifts the alphabet 32 which will convert it to a lowercase char
		@ we need to then branch to encryption with the newly shifted lowercase char
		B encrypt



encrypt:
@ Function to encrypt R0 with R1

	@ Registers used:       r0 = string to encrpt                                           (msg)
	@                       r1 = key to encrypt the string with                             (key)
      	@                       r2 = bit by bit of the message string in r0                     (broken msg)
 	@                       r3 = bit by bit of the key string in r1                         (broken key)
 	@                       r5 = placeholder for r2 so r2 can be used in an other function  (PH broken msg)
 	@                       r6 =    "       "       r1      "       "       "       "       (PH key)
	@                       r7 =    "       "       r0      "       "       "       "       (PH msg)


	@ Key Character Chooser
	LDRB r3, [r1],#1   @ Load incrementing characters of the Key to R3
	CMP r3, #0         @ Compares R3 to ensure it is not null (checks if the string is empty)
	BEQ keyLoop	   @ Branches to keyloop to reset the key if R3 is empty

	eP:
		@ Add the ASCII value of b(98) to offset the value ensuring that the result is only a lower case letter
		ADD r2, r2, #98     @ R2+98 -> R2
		@ Subtract the ASCII values of the key and the message to encryypt the value
		SUB r2, r2, r3	   @ R2-R3 -> R2
		CMP r2, #97
		BLT add26		@ In order to ensure it is a lower case we add 26 if its less than lowercase 'a'
		CMP r2, #122
		BGT minus26		@ In order to ensure it is a lower case we minus 6 if the value is greater than lowercase 'z'
		MOV r5, r2              @ Placeholders to print the encr$
       		MOV r6, r1              @       "       "       "
        	MOV r7, r0              @       "       "       "
        	PUSH {r4,r5,r6,r7,lr}
        	LDR r0, =emsg
       		MOV r1,r2
        	BL printf
		POP {r4,r5,r6,r7,lr}
                MOV r2,r5               @ We remove the placeholders and reutnr the vales back to their original registers
                MOV r1,r6               @       "       "       "
                MOV r0,r7               @       "       "       "
		B cleanLoop		@ We branch back to cleanLoop in order to clean the next char

	keyLoop:
                @ need to loop back to the start of the string and go through it again (if the key is shorter than the message)
                LDR r1,=key1
                LDR r3, [r1], #1
                B eP

	add26:
                ADD r2, r2, #26
		MOV r5, r2		@ Placeholders to print the encrypted value
		MOV r6, r1		@ 	"	"	"
		MOV r7, r0		@	" 	"	"
                PUSH {r4,r5,r6,r7,lr}
		LDR r0, =emsg
		MOV r1,r2		@ We print the encrypted char
		BL printf
		POP {r4,r5,r6,r7,lr}
		MOV r2,r5		@ We remove the placeholders and return the values to their original registers
		MOV r1,r6		@	"	"	"
		MOV r0,r7		@	"	"	"
		B cleanLoop
	minus26:
		SUB r2, r2, #26
		MOV r5, r2            	@ Placeholders to print the encrypted value
	        MOV r6, r1            	@       "       "       "
                MOV r7, r0            	@       "       "       "
                PUSH {r4,r5,r6,r7,lr}
                LDR r0, =emsg		@ We print the encyrpted char
                MOV r1,r2
                BL printf
                POP {r4,r5,r6,r7,lr}
                MOV r2,r5		@ We remove the placeholders and reutnr the vales back to their original registers
        	MOV r1,r6		@	"	"	"
        	MOV r0,r7		@	"	"	"
		B cleanLoop

decrypt:
@ Function to decrypt R0 with R1

        @ Registers used:       r0 = string to decrpt                                           (msg)
        @                       r1 = key to decrypt the string with                             (key)
        @                       r2 = bit by bit of the message string in r0                     (broken msg)
        @                       r3 = bit by bit of the key string in r1                         (broken key)
        @                       r5 = placeholder for r2 so r2 can be used in an other function  (PH broken msg)
        @                       r6 =    "       "       r1      "       "       "       "       (PH key)
        @                       r7 =    "       "       r0      "       "       "       "       (PH msg)

		@ Message Character Chooser
		LDRB r2, [r0],#1   @ Load incrementing characters from the message into R2
		CMP r2, #0	   @ Compares the R2(character) value to ensure it is not null (checks if string is empty)
		BEQ exit	   @ Branch to end if R2 == 0

		@ Key Character Chooser
		LDRB r3, [r1],#1   @ Load incrementing characters of the Key to R3
		CMP r3, #0         @ Compares R3 to ensure it is not null (checks if the string is empty)
		BEQ dKeyLoop	   @ Branches to keyloop to reset the key if R3 is empty


		dP:
			@ Subtract the ASCII value of b(98) to offset the value ensuring that the result is only a lower case letter
			SUB r2, r2, #98     @ R2+98 -> R2
			@ Add the ASCII values of the key and the message to decryypt the value
			ADD r2, r2, r3	   @ R2-R3 -> R2
			CMP r2, #122
			BGT dMinus26		@ In order to ensure it is a lower case we minus 26 if its less than lowercase 'a'
			CMP r2, #97
			BLT dAdd26		@ In order to ensure it is a lower case we add 26 if the value is greater than lowercase 'z'
			MOV r5, r2              @ Placeholders to print the encr$
        	        MOV r6, r1              @       "       "       "
               		MOV r7, r0              @       "       "       "
               	 	PUSH {r4,r5,r6,r7,lr}
                	LDR r0, =emsg		@ We print the decrypted char
                	MOV r1,r2
                	BL printf
                	POP {r4,r5,r6,r7,lr}
                	MOV r2,r5		@ We remove the placeholders and reutnr the vales back to their original registers
                	MOV r1,r6               	@       "       "       "
                	MOV r0,r7               	@       "       "       "
                	B decrypt             	@ We branch back to decrypt in order to clean the next char

		dKeyLoop:
                        @ need to loop back to the start of the string and go through
                        @ it again
                        LDR r1,=key1
                        LDR r3, [r1], #1
                        B dP

		dAdd26:
                        ADD r2, r2, #26
			MOV r5, r2		@ Placeholders to print the encrypted value
			MOV r6, r1		@ 	"	"	"
			MOV r7, r0		@	" 	"	"
                        PUSH {r4,r5,r6,r7,lr}
			LDR r0, =emsg		@ load in decrypted char to print
			MOV r1,r2
			BL printf
			POP {r4,r5,r6,r7,lr}
			MOV r2,r5               @ We remove the placeholders and reutnr the vales back to their original registers
                        MOV r1,r6                       @       "       "       "
                        MOV r0,r7                       @       "       "       "
                        B decrypt               @ We branch back to decrypt in order to clean the next char

		dMinus26:
			SUB r2, r2, #26
			MOV r5, r2            	@ Placeholders to print the encrypted value
                        MOV r6, r1            	@       "       "       "
                        MOV r7, r0          	@       "       "       "
                        PUSH {r4,r5,r6,r7,lr}
                        LDR r0, =emsg		@ We load in decrypted char to print
                        MOV r1,r2
                        BL printf
                        POP {r4,r5,r6,r7,lr}
                        MOV r2,r5               @ We remove the placeholders and reutnr the vales back to their original registers
                        MOV r1,r6                       @       "       "       "
                        MOV r0,r7                       @       "       "       "
                        B decrypt               @ We branch back to decrypt in order to clean the next char

selection:
        PUSH {r4, lr}
        LDR r0, =prompt1                @ prompts user for number
        BL printf

        LDR r0,=scan_pattern            @ reads user's num
        LDR r1,=number_read             @ stores user's num
        BL scanf
	LDR r1,=number_read
	LDR r1, [r1]			@ User number is now stored into r1
	POP {r4,lr}
        CMP r1, #1
	BEQ dOpt			@ If the user inpuut 1 it means they want to decrypt therefore we branch to there
	CMP r1, #1
	BGT tryAgain			@ If the user inputs anything greater than 1 it means there was an error and we need another input
	CMP r1, #0
	BEQ eOpt			@ If the user inputs 0 it means they want to encrypt therefore we brunch to the clean function to start it
	CMP r1,#0
	BLT tryAgain			@ If the user inputs anything less than 0 it means there was an error and we need another input

eOpt:
	@ Function to handle if the user wants to encryppt
	LDR r0,=message
	LDR r1,=key1
	B clean

dOpt:
	@ Function to handle if the user wants to decrypt
	LDR r0,=message
	LDR r1,=key1
	B decrypt

tryAgain:
        PUSH {r4, lr}
        LDR r0, =prompt2                @ prompts user to enter a number within the range this time round
        BL printf
        POP {r4,lr}
        B selection


main:
@	LDR r0,=message
@	LDR r1,=key1
@	B clean
	B selection
