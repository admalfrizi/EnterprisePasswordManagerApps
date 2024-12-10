package org.apps.simpenpass.utils

import io.ktor.utils.io.core.toByteArray

@OptIn(ExperimentalUnsignedTypes::class)
class CamelliaCrypto {
    private val SIGMA = ulongArrayOf(
        0xA09E6673FBCC908BUL, 0xB67AE8584CAA73B2UL,
        0xC6EF372FE94F82BEUL, 0x54FF53A5F1D36F1CUL,
        0x10E527FADE682D1DUL, 0xB05688C2B3E6C1FDUL
    )

    private val SBOX1 = intArrayOf(
        112, 130, 44, 236, 179, 39, 192, 229, 228, 133, 87, 53, 234, 12, 174, 65,
        35, 239, 107, 147, 69, 25, 165, 33, 237, 14, 79, 78, 29, 101, 146, 189,
        134, 184, 175, 143, 124, 235, 31, 206, 62, 48, 220, 95, 94, 197, 11, 26,
        166, 225, 57, 202, 213, 71, 93, 61, 217, 1, 90, 214, 81, 86, 108, 77,
        139, 13, 154, 102, 251, 204, 176, 45, 116, 18, 43, 32, 240, 177, 132, 153,
        223, 76, 203, 194, 52, 126, 118, 5, 109, 183, 169, 49, 209, 23, 4, 215,
        20, 88, 58, 97, 222, 27, 17, 28, 50, 15, 156, 22, 83, 24, 242, 34,
        254, 68, 207, 178, 195, 181, 122, 145, 36, 8, 232, 168, 96, 252, 105, 80,
        170, 208, 160, 125, 161, 137, 98, 151, 84, 91, 30, 149, 224, 255, 100, 210,
        16, 196, 0, 72, 163, 247, 117, 219, 138, 3, 230, 218, 9, 63, 221, 148,
        135, 92, 131, 2, 205, 74, 144, 51, 115, 103, 246, 243, 157, 127, 191, 226,
        82, 155, 216, 38, 200, 55, 198, 59, 129, 150, 111, 75, 19, 190, 99, 46,
        233, 121, 167, 140, 159, 110, 188, 142, 41, 245, 249, 182, 47, 253, 180, 89,
        120, 152, 6, 106, 231, 70, 113, 186, 212, 37, 171, 66, 136, 162, 141, 250,
        114, 7, 185, 85, 248, 238, 172, 10, 54, 73, 42, 104, 60, 56, 241, 164,
        64, 40, 211, 123, 187, 201, 67, 193, 21, 227, 173, 244, 119, 199, 128, 158
    )

    private val SBOX2 = IntArray(256) { index ->
        (SBOX1[index] shl 1 and 0xFF) or (SBOX1[index] shr 7)
    }

    private val SBOX3 = IntArray(256) { index ->
        (SBOX1[index] shl 7 and 0xFF) or (SBOX1[index] shr 1)
    }

    private val SBOX4 = generateSBOX4(SBOX1)

    private fun cycleShift(value: Int, count: Int): Int {
        val temp = (value) ushr (32 - count)
        val show = ((value shl count) + temp)
        return show
    }

    private fun circularLeftShift(value: Int, shift: Int, bitSize: Int = 32): Int {
        return (value shl shift) or (value ushr (bitSize - shift))
    }

    private fun generateSBOX4(sbox1: IntArray): IntArray {
        val sboxSize = sbox1.size
        val sbox4 = IntArray(sboxSize)

        for (x in sbox1.indices) {
            val shiftedX = circularLeftShift(x, 1, bitSize = 8)
            sbox4[x] = sbox1[shiftedX and 0xFF]
        }

        return sbox4
    }


    private fun cycleShiftForPair(values: ULongArray, count: Int): ULongArray {
        val temp = ULongArray(2)
        val newVL = ULongArray(2)
        if (count <= 64) {
            temp[1] = (values[0] shr (64 - count))
            newVL[0] = (values[0] shl count) + (values[1] shr (64 - count))
            newVL[1] = (values[1] shl count) + temp[1]
        } else {
            temp[0] = values[0] shr (64 - (count - 64))
            temp[1] = (values[0] shl (count - 64)) + (values[1] shr (64 - (count - 64)))
            newVL[0] = (values[1] shl (64 - (128 - count))) + temp[0]
            newVL[1] = temp[1]
        }
        return newVL
    }


    private fun getSubkeys128(KL_KR: ULongArray, KA_KB: ULongArray): ULongArray {
        val subkeys = ULongArray(26)

        val KL = ulongArrayOf(KL_KR[0], KL_KR[1])
        val KA = ulongArrayOf(KA_KB[0], KA_KB[1])

        subkeys[0] = KL[0]
        subkeys[1] = KL[1]
        subkeys[2] = KA[0]
        subkeys[3] = KA[1]

        subkeys[4] = cycleShiftForPair(KL, 15)[0]
        subkeys[5] = cycleShiftForPair(KL, 15)[1]
        subkeys[6] = cycleShiftForPair(KA, 15)[0]
        subkeys[7] = cycleShiftForPair(KA, 15)[1]
        subkeys[8] = cycleShiftForPair(KA, 30)[0]
        subkeys[9] = cycleShiftForPair(KA, 30)[1]
        subkeys[10] = cycleShiftForPair(KL, 45)[0]
        subkeys[11] = cycleShiftForPair(KL, 45)[1]
        subkeys[12] = cycleShiftForPair(KA, 45)[0]
        subkeys[13] = cycleShiftForPair(KL, 60)[1]
        subkeys[14] = cycleShiftForPair(KA, 60)[0]
        subkeys[15] = cycleShiftForPair(KA, 60)[1]
        subkeys[16] = cycleShiftForPair(KL, 77)[0]
        subkeys[17] = cycleShiftForPair(KL, 77)[1]
        subkeys[18] = cycleShiftForPair(KL, 94)[0]
        subkeys[19] = cycleShiftForPair(KL, 94)[1]
        subkeys[20] = cycleShiftForPair(KA, 94)[0]
        subkeys[21] = cycleShiftForPair(KA, 94)[1]
        subkeys[22] = cycleShiftForPair(KL, 111)[0]
        subkeys[23] = cycleShiftForPair(KL, 111)[1]
        subkeys[24] = cycleShiftForPair(KA, 111)[0]
        subkeys[25] = cycleShiftForPair(KA, 111)[1]

        return subkeys
    }

    fun getSubKeys192_256Bit(KL_KR: ULongArray, KA_KB: ULongArray) : ULongArray{
        val subkeys = ULongArray(34)
        val KL = ulongArrayOf(KL_KR[0], KL_KR[1])
        val KR = ulongArrayOf(KL_KR[2], KL_KR[3])
        val KA = ulongArrayOf(KA_KB[0], KA_KB[1])
        val KB = ulongArrayOf(KA_KB[2], KA_KB[3])

        subkeys[0] = KL[0]
        subkeys[1] = KL[1]
        subkeys[2] = KB[0]
        subkeys[3] = KB[1]
        subkeys[4] = cycleShiftForPair(KR, 15)[0]
        subkeys[5] = cycleShiftForPair(KR, 15)[1]
        subkeys[6] = cycleShiftForPair(KA, 15)[0]
        subkeys[7] = cycleShiftForPair(KA, 15)[1]
        subkeys[8] = cycleShiftForPair(KR, 30)[0]
        subkeys[9] = cycleShiftForPair(KR, 30)[1]
        subkeys[10] = cycleShiftForPair(KB, 30)[0]
        subkeys[11] = cycleShiftForPair(KB, 30)[1]
        subkeys[12] = cycleShiftForPair(KL, 45)[0]
        subkeys[13] = cycleShiftForPair(KL, 45)[1]
        subkeys[14] = cycleShiftForPair(KA, 45)[0]
        subkeys[15] = cycleShiftForPair(KA, 45)[1]
        subkeys[16] = cycleShiftForPair(KL, 60)[0]
        subkeys[17] = cycleShiftForPair(KL, 60)[1]
        subkeys[18] = cycleShiftForPair(KR, 60)[0]
        subkeys[19] = cycleShiftForPair(KR, 60)[1]
        subkeys[20] = cycleShiftForPair(KB, 60)[0]
        subkeys[21] = cycleShiftForPair(KB, 60)[1]
        subkeys[22] = cycleShiftForPair(KL, 77)[0]
        subkeys[23] = cycleShiftForPair(KL, 77)[1]
        subkeys[24] = cycleShiftForPair(KA, 77)[0]
        subkeys[25] = cycleShiftForPair(KA, 77)[1]
        subkeys[26] = cycleShiftForPair(KR, 94)[0]
        subkeys[27] = cycleShiftForPair(KR, 94)[1]
        subkeys[28] = cycleShiftForPair(KA, 94)[0]
        subkeys[29] = cycleShiftForPair(KA, 94)[1]
        subkeys[30] = cycleShiftForPair(KL, 111)[0]
        subkeys[31] = cycleShiftForPair(KL, 111)[1]
        subkeys[32] = cycleShiftForPair(KB, 111)[0]
        subkeys[33] = cycleShiftForPair(KB, 111)[1]

        return subkeys
    }


    fun generateSubkeys(key: String): ULongArray {
        val KL_KR = getKLKR(key)
        val KA_KB = getKAKB(KL_KR)

        return if (key.length * 8 <= 128) {
            getSubkeys128(KL_KR, KA_KB)
        } else {
            getSubKeys192_256Bit(KL_KR,KA_KB)
        }
    }


    fun getKLKR(stringKey: String): ULongArray {
        val key = ULongArray(4)
        val byteKey = stringKey.toByteArray()

        var c = -1
        for (i in 0 until (if (byteKey.size > 32) 32 else byteKey.size)) {
            if (i % 8 == 0) c++
            key[c] = key[c] shl 8
            key[c] += byteKey[i].toULong() and 0xFFUL
        }

        if (byteKey.size <= 24 && byteKey.size > 16) {
            key[3] = key[2].inv()
        }

        return key
    }


    private fun getKAKB(KL_KR: ULongArray): ULongArray {
        var KA_KB = ULongArray(4)
        var D1 = KL_KR[0] xor KL_KR[2]
        var D2 = KL_KR[1] xor KL_KR[3]
        D2 = FFuncSubkeys(D1, SIGMA[0]) xor D2
        D1 = FFuncSubkeys(D2, SIGMA[1]) xor D1
        D1 = D1 xor KL_KR[0]
        D2 = D2 xor KL_KR[1]
        D2 = FFuncSubkeys(D1, SIGMA[2]) xor D2
        D1 = FFuncSubkeys(D2, SIGMA[3]) xor D1
        KA_KB[0] = D2
        KA_KB[1] = D1
        D1 = KA_KB[0] xor KL_KR[2]
        D2 = KA_KB[1] xor KL_KR[3]
        D2 = FFuncSubkeys(D1, SIGMA[4]) xor D2
        D1 = FFuncSubkeys(D2, SIGMA[5]) xor D1
        KA_KB[2] = D2
        KA_KB[3] = D1

        return KA_KB
    }


    fun FFuncSubkeys(data: ULong, subkey: ULong): ULong {
        var x = data xor subkey
        var result = 0UL
        val t = ULongArray(8)
        val listT = ULongArray(8)

        t[0] = (x shr 56)
        t[1] = ((x shr 48) and 0xFFUL)
        t[2] = ((x shr 40) and 0xFFUL)
        t[3] = ((x shr 32) and 0xFFUL)
        t[4] = ((x shr 24) and 0xFFUL)
        t[5] = ((x shr 16) and 0xFFUL)
        t[6] = ((x shr 8) and 0xFFUL)
        t[7] = ((x shr 0) and 0xFFUL)


        listT[0]= SBOX1[t[0].toInt()].toULong()
        listT[1]= SBOX4[t[1].toInt()].toULong()
        listT[2]= SBOX3[t[2].toInt()].toULong()
        listT[3]= SBOX2[t[3].toInt()].toULong()
        listT[4]= SBOX4[t[4].toInt()].toULong()
        listT[5]= SBOX3[t[5].toInt()].toULong()
        listT[6]= SBOX2[t[6].toInt()].toULong()
        listT[7]= SBOX1[t[7].toInt()].toULong()

        for (i in 0..7) {
            result = result shl 8
            result += (listT[i].toInt() and 0xFF).toULong()
        }

        return result
    }


    private fun FFunc(data: ULong, subkey: ULong): ULong {
        var x = data xor subkey
        var result = 0UL
        val t = ULongArray(8)
        val y = ULongArray(8)
        val listT = ULongArray(8)

        t[0] = (x shr 56)
        t[1] = ((x shr 48) and 0xFFUL)
        t[2] = ((x shr 40) and 0xFFUL)
        t[3] = ((x shr 32) and 0xFFUL)
        t[4] = ((x shr 24) and 0xFFUL)
        t[5] = ((x shr 16) and 0xFFUL)
        t[6] = ((x shr 8) and 0xFFUL)
        t[7] = ((x shr 0) and 0xFFUL)

        listT[0]= SBOX1[t[0].toInt()].toULong()
        listT[1]= SBOX4[t[1].toInt()].toULong()
        listT[2]= SBOX3[t[2].toInt()].toULong()
        listT[3]= SBOX2[t[3].toInt()].toULong()
        listT[4]= SBOX4[t[4].toInt()].toULong()
        listT[5]= SBOX3[t[5].toInt()].toULong()
        listT[6]= SBOX2[t[6].toInt()].toULong()
        listT[7]= SBOX1[t[7].toInt()].toULong()

        val reverseList = listT.reversedArray()

        // Combine the results
        y[0] = (reverseList[0] xor reverseList[2] xor reverseList[3] xor reverseList[5] xor reverseList[6] xor reverseList[7])
        y[1] = (reverseList[0] xor reverseList[1] xor reverseList[3] xor reverseList[4] xor reverseList[6] xor reverseList[7])
        y[2] = (reverseList[0] xor reverseList[1] xor reverseList[2] xor reverseList[4] xor reverseList[5] xor reverseList[7])
        y[3] = (reverseList[1] xor reverseList[2] xor reverseList[3] xor reverseList[4] xor reverseList[5] xor reverseList[6])
        y[4] = (reverseList[0] xor reverseList[1] xor reverseList[5] xor reverseList[6] xor reverseList[7])
        y[5] = (reverseList[1] xor reverseList[2] xor reverseList[4] xor reverseList[6] xor reverseList[7])
        y[6] = (reverseList[2] xor reverseList[3] xor reverseList[4] xor reverseList[5] xor reverseList[7])
        y[7] = (reverseList[0] xor reverseList[3] xor reverseList[4] xor reverseList[5] xor reverseList[6])

        // Combine the results
        for (i in 0..7) {
            result = result shl 8
            result += (y[i].toInt() and 0xFF).toULong()
        }

        return result
    }

    private fun FLFunc(data: ULong, subkey: ULong): ULong {
        var x1 = (data shr 32).toInt()
        var x2 = (data and 0xFFFFFFFFUL).toInt()
        val k1 = (subkey shr 32).toInt()
        val k2 = (subkey and 0xFFFFFFFFUL).toInt()

        x2 = x2 xor cycleShift((x1 and k1), 1)
        x1 = x1 xor (x2 or k2)

        val res = ((x1.toULong() shl 32) or (x2.toULong() and 0xFFFFFFFFUL))

        return res
    }

    private fun FLINVFunc(data: ULong, subkey: ULong): ULong {
        var y1 = (data shr  32).toInt()
        var y2 = (data and 0xFFFFFFFFUL).toInt()
        val k1 = (subkey shr 32).toInt()
        val k2 = (subkey and 0xFFFFFFFFUL).toInt()

        y1 = y1 xor (y2 or k2)
        y2 = y2 xor cycleShift(y1 and k1, 1)

        val res = ((y1.toULong() shl 32) or (y2.toULong() and 0xFFFFFFFFUL))

        return res
    }


    fun crypt(D1: ULong, D2: ULong, subkeys: ULongArray) : ULongArray {
        val size = subkeys.size

        var d1 = D1
        var d2 = D2

        val m1 = subkeys[0] xor d1
        val m2 = subkeys[1] xor d2

        when(size <= 26){
            true -> {
                d2 = FFunc(m1, subkeys[2]) xor m2
                d1 = FFunc(d2, subkeys[3]) xor m1
                d2 = FFunc(d1, subkeys[4]) xor d2
                d1 = FFunc(d2, subkeys[5]) xor d1
                d2 = FFunc(d1, subkeys[6]) xor d2
                d1 = FFunc(d2, subkeys[7]) xor d1
                d1 = FLFunc(d1, subkeys[8])
                d2 = FLINVFunc(d2, subkeys[9])
                d2 = FFunc(d1, subkeys[10]) xor d2
                d1 = FFunc(d2, subkeys[11]) xor d1
                d2 = FFunc(d1, subkeys[12]) xor d2
                d1 = FFunc(d2, subkeys[13]) xor d1
                d2 = FFunc(d1, subkeys[14]) xor d2
                d1 = FFunc(d2, subkeys[15]) xor d1
                d1 = FLFunc(d1, subkeys[16])
                d2 = FLINVFunc(d2, subkeys[17])
                d2 = FFunc(d1, subkeys[18]) xor d2
                d1 = FFunc(d2, subkeys[19]) xor d1
                d2 = FFunc(d1, subkeys[20]) xor d2
                d1 = FFunc(d2, subkeys[21]) xor d1
                d2 = FFunc(d1, subkeys[22]) xor d2
                d1 = FFunc(d2, subkeys[23]) xor d1
                d2 = d2 xor subkeys[24]
                d1 = d1 xor subkeys[25]

            }
            false -> {
                d2 = FFunc(m1, subkeys[2]) xor m2
                d1 = FFunc(d2, subkeys[3]) xor m1
                d2 = FFunc(d1, subkeys[4]) xor d2
                d1 = FFunc(d2, subkeys[5]) xor d1
                d2 = FFunc(d1, subkeys[6]) xor d2
                d1 = FFunc(d2, subkeys[7]) xor d1
                d1 = FLFunc(d1, subkeys[8])
                d2 = FLINVFunc(d2, subkeys[9])
                d2 = FFunc(d1, subkeys[10]) xor d2
                d1 = FFunc(d2, subkeys[11]) xor d1
                d2 = FFunc(d1, subkeys[12]) xor d2
                d1 = FFunc(d2, subkeys[13]) xor d1
                d2 = FFunc(d1, subkeys[14]) xor d2
                d1 = FFunc(d2, subkeys[15]) xor d1
                d1 = FLFunc(d1, subkeys[16])
                d2 = FLINVFunc(d2, subkeys[17])
                d2 = FFunc(d1, subkeys[18]) xor d2
                d1 = FFunc(d2, subkeys[19]) xor d1
                d2 = FFunc(d1, subkeys[20]) xor d2
                d1 = FFunc(d2, subkeys[21]) xor d1
                d2 = FFunc(d1, subkeys[22]) xor d2
                d1 = FFunc(d2, subkeys[23]) xor d1
                d1 = FLFunc(d1, subkeys[24])
                d2 = FLINVFunc(d2, subkeys[25])
                d2 = FFunc(d1, subkeys[26]) xor d2
                d1 = FFunc(d2, subkeys[27]) xor d1
                d2 = FFunc(d1, subkeys[28]) xor d2
                d1 = FFunc(d2, subkeys[29]) xor d1
                d2 = FFunc(d1, subkeys[30]) xor d2
                d1 = FFunc(d2, subkeys[31]) xor d1
                d2 = d2 xor subkeys[32]
                d1 = d1 xor subkeys[33]
            }
        }

        return ulongArrayOf(d2, d1)
    }


    fun cryptRev(D2: ULong, D1: ULong, subkeys: ULongArray) : ULongArray {
        val revKeys = if (subkeys.size <= 26) transformKeys128(subkeys) else transformKeys192_256(subkeys)
        val size = revKeys.size

        var d1 = D2
        var d2 = D1

        d1 = d1 xor revKeys[1]
        d2 = d2 xor revKeys[0]

        when(size <= 26){
            true -> {
                d2 = FFunc(d1, revKeys[2]) xor d2
                d1 = FFunc(d2, revKeys[3]) xor d1
                d2 = FFunc(d1, revKeys[4]) xor d2
                d1 = FFunc(d2, revKeys[5]) xor d1
                d2 = FFunc(d1, revKeys[6]) xor d2
                d1 = FFunc(d2, revKeys[7]) xor d1
                d1 = FLFunc(d1, revKeys[8])
                d2 = FLINVFunc(d2, revKeys[9])
                d2 = FFunc(d1, revKeys[10]) xor d2
                d1 = FFunc(d2, revKeys[11]) xor d1
                d2 = FFunc(d1, revKeys[12]) xor d2
                d1 = FFunc(d2, revKeys[13]) xor d1
                d2 = FFunc(d1, revKeys[14]) xor d2
                d1 = FFunc(d2, revKeys[15]) xor d1
                d1 = FLFunc(d1, revKeys[16])
                d2 = FLINVFunc(d2, revKeys[17])
                d2 = FFunc(d1, revKeys[18]) xor d2
                d1 = FFunc(d2, revKeys[19]) xor d1
                d2 = FFunc(d1, revKeys[20]) xor d2
                d1 = FFunc(d2, revKeys[21]) xor d1
                d2 = FFunc(d1, revKeys[22]) xor d2
                d1 = FFunc(d2, revKeys[23]) xor d1
                d2 = d2 xor revKeys[24]
                d1 = d1 xor revKeys[25]

            }
            false -> {

                d2 = FFunc(d1, revKeys[2]) xor d2
                d1 = FFunc(d2, revKeys[3]) xor d1
                d2 = FFunc(d1, revKeys[4]) xor d2
                d1 = FFunc(d2, revKeys[5]) xor d1
                d2 = FFunc(d1, revKeys[6]) xor d2
                d1 = FFunc(d2, revKeys[7]) xor d1
                d1 = FLFunc(d1, revKeys[8])
                d2 = FLINVFunc(d2, revKeys[9])
                d2 = FFunc(d1, revKeys[10]) xor d2
                d1 = FFunc(d2, revKeys[11]) xor d1
                d2 = FFunc(d1, revKeys[12]) xor d2
                d1 = FFunc(d2, revKeys[13]) xor d1
                d2 = FFunc(d1, revKeys[14]) xor d2
                d1 = FFunc(d2, revKeys[15]) xor d1
                d1 = FLFunc(d1, revKeys[16])
                d2 = FLINVFunc(d2, revKeys[17])
                d2 = FFunc(d1, revKeys[18]) xor d2
                d1 = FFunc(d2, revKeys[19]) xor d1
                d2 = FFunc(d1, revKeys[20]) xor d2
                d1 = FFunc(d2, revKeys[21]) xor d1
                d2 = FFunc(d1, revKeys[22]) xor d2
                d1 = FFunc(d2, revKeys[23]) xor d1
                d1 = FLFunc(d1, revKeys[24])
                d2 = FLINVFunc(d2, revKeys[25])
                d2 = FFunc(d1, revKeys[26]) xor d2
                d1 = FFunc(d2, revKeys[27]) xor d1
                d2 = FFunc(d1, revKeys[28]) xor d2
                d1 = FFunc(d2, revKeys[29]) xor d1
                d2 = FFunc(d1, revKeys[30]) xor d2
                d1 = FFunc(d2, revKeys[31]) xor d1
                d2 = d2 xor revKeys[32]
                d1 = d1 xor revKeys[33]

            }
        }

        return ulongArrayOf(d2, d1)
    }


    fun transformKeys128(subkeys: ULongArray): ULongArray {
        val newSubkeys = ULongArray(subkeys.size)

        newSubkeys[0] = subkeys[25]
        newSubkeys[1] = subkeys[24]
        newSubkeys[24] = subkeys[0]
        newSubkeys[25] = subkeys[1]

        for (i in 2..12) {
            newSubkeys[i] = subkeys[25 - i]
            newSubkeys[25 - i] = subkeys[i]
        }

        return newSubkeys
    }

    fun transformKeys192_256(subkeys: ULongArray): ULongArray {
        val newSubkeys = ULongArray(subkeys.size)
        newSubkeys[0] = subkeys[33]
        newSubkeys[1] = subkeys[32]
        newSubkeys[32] = subkeys[0]
        newSubkeys[33] = subkeys[1]
        for (i in 2..16) {
            newSubkeys[i] = subkeys[33 - i]
            newSubkeys[33 - i] = subkeys[i]
        }
        return newSubkeys
    }

    fun encrypt(data: String, key : String) : String {
        val subkeys = generateSubkeys(key)
        var res: ULongArray

        val cryptVal = stringToULongChunks(data)
        val result = mutableListOf<ULongArray>()

        cryptVal.forEach { (left,right) ->
            res = crypt(left,right,subkeys)
            result.add(res)
        }

        val afterEnc = transformToPairList(result)
        val listEnc = mutableListOf<String>()
        afterEnc.forEach { (r,l) ->
            listEnc.add(longToAsciiString(r,l))
        }

        val enc = listEnc.joinToString("")

        return enc
    }


    fun decrypt(data: String, key : String) : String {
        val subkeys = generateSubkeys(key)
        var res: ULongArray
        val stringToLong = stringToULongChunks(data)
        val result = mutableListOf<ULongArray>()

        stringToLong.forEach { (l,r) ->
            res = cryptRev(l,r,subkeys)
            result.add(res)
        }
        val afterDec = transformToPairList(result)
        val listDec = mutableListOf<String>()

        afterDec.forEach { (l, r) ->
            listDec.add(longToAsciiString(l,r))
        }
        val dec = listDec.joinToString("").trimEnd()

        return dec
    }

    private fun transformToPairList(data: List<ULongArray>): List<Pair<ULong, ULong>> {
        return data.mapNotNull { array ->
            if (array.size == 2) {
                Pair(array[0], array[1])
            } else {
                null // Ignore invalid arrays
            }
        }
    }

    private fun stringToULongChunks(data: String): List<Pair<ULong, ULong>> {
        require(data.isNotEmpty()) { "Input string cannot be empty." }

        val chunks = data.chunked(16)

        fun processChunk(chunk: String): Pair<ULong, ULong> {
            val paddedChunk = chunk.padEnd(16)

            val left = paddedChunk.substring(0, 8)
            val right = paddedChunk.substring(8, 16)

            fun toULong(subChunk: String): ULong {
                return subChunk.fold(0UL) { acc, char ->
                    (acc shl 8) or (char.code.toULong() and 0xFFUL)
                }
            }

            return Pair(toULong(left), toULong(right))
        }

        return chunks.map { processChunk(it) }
    }



    fun longToByte(D1: ULong, D2: ULong): Array<ByteArray> {
        val bytes = Array(2) { ByteArray(8) }
        var d1 = D1
        var d2 = D2

        for (i in 7 downTo 0) {
            bytes[0][i] = (d1 and 0xFFUL).toByte()
            bytes[1][i] = (d2 and 0xFFUL).toByte()
            d1 = d1 shr 8
            d2 = d2 shr 8
        }

        return bytes
    }

    fun byteArrayToAsciiString(byteArray: ByteArray): String {
        return byteArray.joinToString("") { it.toInt().toChar().toString() }
    }

    fun longToAsciiString(D1: ULong, D2: ULong): String {
        val bytes = longToByte(D1, D2)

        val asciiString1 = byteArrayToAsciiString(bytes[0])
        val asciiString2 = byteArrayToAsciiString(bytes[1])
        return asciiString1 + asciiString2
    }
}