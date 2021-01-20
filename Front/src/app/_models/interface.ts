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
    tariff_name: string;
    internet_GBs: number;
    free_minutes: number;
    free_sms: number;
    roaming_per_minute_call_price: number;
    roaming_per_minute_internet_price: number;
    one_sms_price: number;
    minute_of_call_price: number;
}

export interface ConstantPrices {
    id: string;
    fixed_ip_prices: number[];
    ipv6_support_prices: number[];
    installation_prices_for_internet_product: number[];
    installation_prices_for_dtv_product: number[];
    delivery_prices: number[];
    support_of_5g_prices: number[];
    discount: number;
    discountEndDate: string;
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
