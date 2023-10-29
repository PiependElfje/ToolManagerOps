/*
 * <SOURCE HEADER>
 * 
 * <NAME>
 * 
 * <RCS_KEYWORD>
 * $SOURCE:
 * $REVISION:
 * $DATE:
 * </RCS_kEYWORD>
 * 
 * <COPYRIGHT>
 * The following source code is protected under all standard copyright laws.
 * </COPTRIGHT>
 * 
 * </SOURCE HEADER>
 */

package guiops;

// Enum voor de verschillende materiaaltypes
enum OptiesMateriaal {
    ANTENNE,
    USB_STICK,
    BEKABELING,
    SDR,
}

// Enum voor de verschillende subtypen van ANTENNE
enum AntenneType {
    VLAKANTENNE,
    SCHOTELANTENNE
}

// Enum voor de verschillende subtypen van BEKABELING
enum BekabelingType {
    ETHERNET_1M,
    ETHERNET_2M,
    ETHERNET_5M,
    GLAS_1M,
    GLAS_2M,
    GLAS_5M
}

// Enum voor de verschillende subtypen van ANTENNE
enum USBstickType {
    KINGSTON128GB,
    KINGSTON64GB
}

// Enum voor de verschillende subtypen van SDR
enum SoftwaredefinedradioType {
    ETTUSX440,
    ETTUSX300,
    ETTUSN320
}