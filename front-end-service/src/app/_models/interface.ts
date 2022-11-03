import {Plotly} from 'angular-plotly.js/src/app/shared/plotly.interface';

export interface Address {
    addressId: number;
    buildingId: number;
    country: string;
    city: string;
    street: string;
    buildingNum: string;
    latitude: string;
    longitude: string;
    roomNum: string;
    index: string;
    entrance: string;
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
    balance: number;
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

// export interface BillingAccount {
//     id: number;
//     user: User;
//     accountNumber: string;
//     balance: number;
// }

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
    forSale: boolean;
    guarantee: number;
    standards: string[];
    memory: number;
    frequencies: string[];
    protocolsAndTechnologies: string[];
}

export interface AllDevice extends Device {
    used: boolean[];
    macAddresses: string[];
    serialNumbers: string[];
}

export interface UserDevice extends Device {
    macAddress: string;
    serialNumber: string;
}

export interface Cable {
    id: string;
    type: string;
    length: number;
    category: number;
}

export interface OrderValue {
    userId: number;
    selectedProducts: string[];
    selectedPhoneNumber: PhoneNumber;
    selectedTariff: Tariff;
    support5g: boolean;
    deliveryAndActivationMobile: boolean;
    selectedSpeed: InternetOffer;
    selectedDevice: Device;
    selectedChannelNumber: DtvOffer;
    fixedIpSupport: boolean;
    installation: boolean;
    cableLength: number;
    cablePriceTotal: number;
    selectedAddress: Address;
    // selectedAccount: BillingAccount;
    deliveryDateStr: string;
    deliveryTime: string;
    totalNRC: number;
    totalMRC: number;
    deliveryPrice: number;
    constantPrices: ConstantPrices;
    orderAim: string;
    order: Order;
    macAddress: string;
    deliveryId: number;
}

export interface ProductInstance {
    id: number;
    instanceId: number;
    userId: number;
    status: string;
    activatedDateStr: string;
    disconnectedDateStr: string;
    expiredDate: string;
    // billingAccountId: number;
    product: string;
}

export interface MobileProductInstance extends ProductInstance {
    tariff: Tariff;
    phoneNumber: PhoneNumber;
    balance: number;
    support5g: boolean;
}

export interface InternetProductInstance extends ProductInstance {
    address: Address;
    internetOffer: InternetOffer;
    fixedIp: string;
    cableLen: number;
    device: UserDevice;
}

export interface DtvProductInstance extends ProductInstance {
    internetProductId: number;
    dtvOffer: DtvOffer;
}

export interface Order {
    id: number;
    userId: number;
    instanceId: number;
    status: string;
    startDateStr: string;
    completionDateStr: string;
    totalNRC: number;
    totalMRC: number;
    orderAim: string;
    workersNum: number;
}

export interface OrderWithInstances {
    order: Order;
    tasks: Task[];
    productInstances: ProductInstance[];
}

export interface Task {
    id: number;
    name: string;
    type: string;
    description: string;
    status: string;
    startDateStr: string;
    completionDateStr: string;
}

export interface DeliveryAdditionalInfo {
    macAddress: string;
    deviceSerialNumber: string;
    simCardNumber: string;
    cableLength: string;
}

export interface Delivery {
    id: number;
    orderId: number;
    deliveryDateStr: string;
    duration: number;
    status: string;
    address: Address;
    products: string;
    needInfo: boolean;
    responsible: boolean;
    deliveryStarted: boolean;
    additionalInfo: DeliveryAdditionalInfo;
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

export interface ChartData {
    x: object[];
    y: object[];
}
export interface GraphParams {
    data: Plotly.Data[];
    layout: object;
}

export interface CohortAnalysis {
    userNumber: number;
    userNumberByProducts: ChartData;
    usersByDate: ChartData;
    productsByDate: ChartData[];
}

export interface BusinessMetrics {
    profitByDate: ChartData;
    profit: number;
    aovsByDate: ChartData[];
    aovs: number[];
    arppuByDate: ChartData;
    arppu: number;
}
