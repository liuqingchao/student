

/**
 * ICBCService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package axis2.service.server;

    /*
     *  ICBCService java interface
     */

    public interface ICBCService {
          

        /**
          * Auto generated method signature
          * Post调用银行支付接口
                    * @param postOrderForm0
                
         */

         
                     public cn.edu.huat.pay.PostOrderFormResponse postOrderForm(

                        cn.edu.huat.pay.PostOrderForm postOrderForm0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Post调用银行支付接口
                * @param postOrderForm0
            
          */
        public void startpostOrderForm(

            cn.edu.huat.pay.PostOrderForm postOrderForm0,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 生成订单信息
                    * @param signData2
                
         */

         
                     public cn.edu.huat.pay.SignDataResponse signData(

                        cn.edu.huat.pay.SignData signData2)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 生成订单信息
                * @param signData2
            
          */
        public void startsignData(

            cn.edu.huat.pay.SignData signData2,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param verify4
                
         */

         
                     public cn.edu.huat.pay.VerifyResponse verify(

                        cn.edu.huat.pay.Verify verify4)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param verify4
            
          */
        public void startverify(

            cn.edu.huat.pay.Verify verify4,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 订单支付
                    * @param postOrder6
                
         */

         
                     public cn.edu.huat.pay.PostOrderResponse postOrder(

                        cn.edu.huat.pay.PostOrder postOrder6)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 订单支付
                * @param postOrder6
            
          */
        public void startpostOrder(

            cn.edu.huat.pay.PostOrder postOrder6,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 查询订单信息
                    * @param getOrderInfo8
                
         */

         
                     public cn.edu.huat.pay.GetOrderInfoResponse getOrderInfo(

                        cn.edu.huat.pay.GetOrderInfo getOrderInfo8)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 查询订单信息
                * @param getOrderInfo8
            
          */
        public void startgetOrderInfo(

            cn.edu.huat.pay.GetOrderInfo getOrderInfo8,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 查询订单信息
                    * @param getPayInfo10
                
         */

         
                     public cn.edu.huat.pay.GetPayInfoResponse getPayInfo(

                        cn.edu.huat.pay.GetPayInfo getPayInfo10)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 查询订单信息
                * @param getPayInfo10
            
          */
        public void startgetPayInfo(

            cn.edu.huat.pay.GetPayInfo getPayInfo10,

            final axis2.service.server.ICBCServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    