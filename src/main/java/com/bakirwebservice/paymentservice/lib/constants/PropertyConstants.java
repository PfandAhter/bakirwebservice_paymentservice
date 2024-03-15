package com.bakirwebservice.paymentservice.lib.constants;

public class PropertyConstants {
    //MICROSERVICE PATHS
    public static final String REQUEST_RESTTEMPLATE_MICROSERVICE_TOKEN_SERVICE_EXTRACT_USERNAME = "${app.microservicePaths.tokenService.extractUsername}";

    public static final String REQUEST_RESTTEMPLATE_MICROSERVICE_PRODUCT_SERVICE_PRODUCT_GET_PRODUCT_INFO = "${app.microservicePaths.productService.getProductInfo}";



    //CONTROLLER PATHS
    public static final String REQUEST_PAYMENTSERVICE = "${app.routesController.requestmapping.paymentServiceController}";

    public static final String REQUEST_BALANCE_ADD = "${app.routes.balance.addBalance}";

    public static final String REQUEST_BALANCE_CREATE = "${app.routes.balance.createBalance}";

    public static final String REQUEST_BALANCE_GET = "${app.routes.balance.getBalance}";

    public static final String REQUEST_ORDERLIST_GET_LIST = "${app.routes.orderList.getOrderList}";

    public static final String REQUEST_ORDERLIST_CREATE_ORDER = "${app.routes.orderList.createOrder}";

    public static final String REQUEST_ORDERLIST_DELETE_BY_PRODUCT_CODE = "${app.routes.orderList.deleteByProductCode}";

}
