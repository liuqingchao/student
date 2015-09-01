
/**
 * ICBCServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package axis2.service.server;

    /**
     *  ICBCServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ICBCServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ICBCServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ICBCServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for postOrderForm method
            * override this method for handling normal response from postOrderForm operation
            */
           public void receiveResultpostOrderForm(
                    cn.edu.huat.pay.PostOrderFormResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from postOrderForm operation
           */
            public void receiveErrorpostOrderForm(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for signData method
            * override this method for handling normal response from signData operation
            */
           public void receiveResultsignData(
                    cn.edu.huat.pay.SignDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from signData operation
           */
            public void receiveErrorsignData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verify method
            * override this method for handling normal response from verify operation
            */
           public void receiveResultverify(
                    cn.edu.huat.pay.VerifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verify operation
           */
            public void receiveErrorverify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for postOrder method
            * override this method for handling normal response from postOrder operation
            */
           public void receiveResultpostOrder(
                    cn.edu.huat.pay.PostOrderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from postOrder operation
           */
            public void receiveErrorpostOrder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOrderInfo method
            * override this method for handling normal response from getOrderInfo operation
            */
           public void receiveResultgetOrderInfo(
                    cn.edu.huat.pay.GetOrderInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOrderInfo operation
           */
            public void receiveErrorgetOrderInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPayInfo method
            * override this method for handling normal response from getPayInfo operation
            */
           public void receiveResultgetPayInfo(
                    cn.edu.huat.pay.GetPayInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPayInfo operation
           */
            public void receiveErrorgetPayInfo(java.lang.Exception e) {
            }
                


    }
    