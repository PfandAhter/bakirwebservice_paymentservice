package com.bakirwebservice.paymentservice.lib.constants;

public class PropertyConstants {
    //CONTROLLER PATHS
    public static final String REQUEST_SECURE_SERVICE_PAYMENT_CONTROLLER = "${app.routesController.controllers.secure.paymentServiceController}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_GET = "${app.routesController.requestMapping.secure.payment-service.getItemsInCart}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_ADD = "${app.routesController.requestMapping.secure.payment-service.createOrder}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_DELETE_ORDER_BY_PRODUCT_CODE = "${app.routesController.requestMapping.secure.payment-service.deleteByProductCode}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_SHOPPING_CART_CHECKOUT = "${app.routesController.requestMapping.secure.payment-service.buyItemsInCart}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_QUERY_BY_TRACKING_NUMBER = "${app.routesController.requestMapping.secure.payment-service.queryByTrackingNumber}";

    public static final String REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_IS_BOUGHT = "${app.routesController.requestMapping.secure.payment-service.userOrderedProduct}";
}
