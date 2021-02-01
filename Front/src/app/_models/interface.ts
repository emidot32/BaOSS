export interface Address {
    id: number;
    country: string;
    city: string;
    street: string;
    buildingNum: string;
    roomNum: string;
    index: number;
}

export interface User {
    id: number;
    login: string;
    password: string;
    name: string;
    surname: string;
    email: string;
    phoneNumber: string;
    idCardNumber: string;
    contractNumber: number;
    birthday: Date;
    gender: string;
    activityStatus: string;
    regDate: Date;
    role: string;
    minRefreshDate: Date;
    addresses: Address[];
    token: string;
}

export interface InternetOffer {
    id: string;
    speed: number;
    startingPrice: number;
    discountedPrice: number;
    discount: number;
    discountEndDate: string;
    priceEnding: string;
}

export interface DtvOffer {
    id: string;
    channelNumber: number;
    startingPrice: number;
    discountedPrice: number;
    discount: number;
    discountEndDate: string;
    priceEnding: string;
}

export interface Tariff {
    id: string;
    startingPrice: number;
    discountedPrice: number;
    discount: number;
    discountEndDate: string;
    priceEnding: string;
    tariffName: string;
    internetGBs: number;
    freeMinutes: number;
    freeSms: number;
    roamingPerMinuteCallPrice: number;
    roamingPerMinuteInternetPrice: number;
    oneSmsPrice: number;
    minuteOfCallPrice: number;
}

export interface ConstantPrices {
    id: string;
    fixedIpPrices: number[];
    ipv6SupportPrices: number[];
    installationPricesForInternetProduct: number[];
    installationPricesForDtvProduct: number[];
    deliveryPrices: number[];
    supportOf5gPrices: number[];
    cableOneMeterPrice: number[];
    discount: number;
    discountEndDate: string;
}

export interface Discount {
    id: string;
    value: number;
    startDate: Date;
    endDate: Date;
    appliedFor: string;
}

export interface BillingAccount {
    id: number;
    user: User;
    accountNumber: string;
    balance: number;
}

export interface PhoneNumber {
    id: string;
    phoneNumber: string;
    price: number;
    used: boolean;
    simCardNumber: string;
    countryCode: string;
    pinCode: string;
    pukCode: string;
}
export interface Device {
    id: string;
    name: string;
    type: string;
    price: number;
    throughput: string;
    portsNum: number;
    portTypes: string;
    used: boolean[];
    forSale: boolean;
    guarantee: number;
    standards: string;
    memory: number;
    macAddresses: string[];
    serialNumbers: string[];
    frequencies: string;
    protocolsAndTechnologies: string;
}
export interface Cable {
    id: string;
    type: string;
    length: number;
    category: number;
}

export interface Menu {
    name: string;
    url: string;
}

export interface Notification {
    notificationId: number;
    userId: number;
    notifDate: Date;
    isRead: boolean;
    fromUserId: number;
    notifTypeId: number;
    overviewId: number;
    reviewId: number;
    bookId: number;
    achievId: number;
    overviewName: string;
    reviewName: string;
    fromUserName: string;
    bookName: string;
    achievName: string;
    notifTitle: string;
    notifText: string;
}

export interface Message {
    message: string;
    fromName: string;
    toId: number;
    dateTimeSend: Date;
}


export interface Toaster {
    status: string;
    message: string;
}
