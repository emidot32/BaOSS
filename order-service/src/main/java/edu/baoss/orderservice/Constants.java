package edu.baoss.orderservice;

import java.text.SimpleDateFormat;

public final class Constants {
    public final static SimpleDateFormat ONLY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public final static SimpleDateFormat DATE_AND_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public final static SimpleDateFormat DATE_HOUR_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH");
    public final static SimpleDateFormat ONLY_HOUR_FORMAT = new SimpleDateFormat("HH");

    // Names of products
    public final static String DTV_PRODUCT_STR = "DTV Product";
    public final static String INTERNET_PRODUCT_STR = "Internet Product";
    public final static String MOBILE_PRODUCT_STR = "Mobile Product";

    // RabbitMQ constants
    public final static String START = "start";
    public final static String CONTINUE = "continue";
    public final static String ORDER_FULFILMENT_EXCHANGE = "order-fulfilment-exchange";
    public final static String START_ORDER_FULFILMENT_QUEUE = "start-order-fulfilment-queue";
    public final static String CONTINUE_ORDER_FULFILMENT_QUEUE = "continue-order-fulfilment-queue";

    // Tasks
    public final static String INIT_ORDER_TASK_NAME = "Initialize Order";
    public final static String INIT_ORDER_TASK_DESCRIPTION = "Task for order and instances creating. " +
            "Also this task starts provisioning flow.";

    public final static String RESERVE_SIM_CARD_TASK_NAME = "Reserve SIM Card";
    public final static String RESERVE_SIM_CARD_TASK_DESCRIPTION = "Reserve SIM Card for the user.";

    public final static String RESERVE_DEVICE_TASK_NAME = "Reserve Device";
    public final static String RESERVE_DEVICE_TASK_DESCRIPTION = "Reserve the device for the user.";

    public final static String RESERVE_CABLE_TASK_NAME = "Reserve Cable";
    public final static String RESERVE_CABLE_TASK_DESCRIPTION = "Reserve specified cable length for the user.";

    public final static String NOTIFY_USER_TASK_NAME = "Notify User";
    public final static String NOTIFY_USER_TASK_DESCRIPTION = "Remind the user about the order delivery date.";

    public final static String NOTIFY_FITTER_TASK_NAME = "Notify the fitter about installation date";
    public final static String NOTIFY_FITTER_TASK_DESCRIPTION = "Send notification to the fitter about the order installation date.";

    public final static String NOTIFY_DELIVERYMAN_TASK_NAME = "Notify the deliveryman about delivery date";
    public final static String NOTIFY_DELIVERYMAN_TASK_DESCRIPTION = "Send notification to the deliveryman about the order delivery date.";

    public final static String ACTIVATE_5G_TASK_NAME = "Activate 5G";
    public final static String ACTIVATE_5G_TASK_DESCRIPTION = "Activate 5G for SIM card.";

    public final static String WAITING_FITTER_CONFIRMATION_TASK_NAME = "Waiting Fitter Confirmation of Installation";
    public final static String WAITING_MAC_ADDRESS_TASK_NAME = "Waiting MAC Address for Internet connection";
    public final static String WAITING_DELIVERYMAN_CONFIRMATION_TASK_NAME = "Waiting Deliveryman Confirmation of Delivery";
    public final static String WAITING_DELIVERYMAN_CONFIRMATION_TASK_DESCRIPTION = "Waiting when the deliveryman confirm success delivery.";

    public final static String WAITING_USER_CONFIRMATION_TASK_NAME = "Waiting User Confirmation of SIM Card Getting";
    public final static String WAITING_USER_CONFIRMATION_TASK_DESCRIPTION = "Waiting when the user confirm that he get SIM card from store.";

    public final static String CONNECT_TO_NETWORK_TASK_NAME = "Connect User to Network";
    public final static String CONNECT_TO_NETWORK_TASK_DESCRIPTION = "Finalize user connection to network.";

    public final static String ACTIVATE_DTV_CHANNELS_TASK_NAME = "Activate DTV Channels";
    public final static String ACTIVATE_DTV_CHANNELS_TASK_DESCRIPTION = "Finalize activation of DTV.";

    public final static String PROVIDE_FIXED_IP_TASK_NAME = "Provide Fixed IP";
    public final static String PROVIDE_FIXED_IP_TASK_DESCRIPTION = "Providing Fixed IP for user.";

    public final static String NRC_PAYMENT_TASK_NAME = "NRC Payment";
    public final static String NRC_PAYMENT_TASK_DESCRIPTION = "Get NRC payment from the card.";

    public final static String COMPLETE_ORDER_TASK_NAME = "Complete Order";
    public final static String COMPLETE_ORDER_TASK_DESCRIPTION = "Final processes for order fulfilment.";

    // Task params
    public final static String AFTER_DELIVERY = "after_delivery";

}
