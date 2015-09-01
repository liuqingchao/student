
/**
 * ICBC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */
            
                package cn.edu.huat.pay;
            

            /**
            *  ICBC bean class
            */
        
        public  class ICBC
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = ICBC
                Namespace URI = http://pay.huat.edu.cn/
                Namespace Prefix = ns1
                */
            

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://pay.huat.edu.cn/")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        

                        /**
                        * field for IsCheck
                        */

                        
                                    protected boolean localIsCheck ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIsCheck(){
                               return localIsCheck;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IsCheck
                               */
                               public void setIsCheck(boolean param){
                            
                                            this.localIsCheck=param;
                                    

                               }
                            

                        /**
                        * field for MerReference
                        */

                        
                                    protected java.lang.String localMerReference ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerReferenceTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerReference(){
                               return localMerReference;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerReference
                               */
                               public void setMerReference(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerReferenceTracker = true;
                                       } else {
                                          localMerReferenceTracker = false;
                                              
                                       }
                                   
                                            this.localMerReference=param;
                                    

                               }
                            

                        /**
                        * field for TranData
                        */

                        
                                    protected java.lang.String localTranData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTranDataTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTranData(){
                               return localTranData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TranData
                               */
                               public void setTranData(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localTranDataTracker = true;
                                       } else {
                                          localTranDataTracker = false;
                                              
                                       }
                                   
                                            this.localTranData=param;
                                    

                               }
                            

                        /**
                        * field for OrderPostUrl
                        */

                        
                                    protected java.lang.String localOrderPostUrl ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderPostUrlTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderPostUrl(){
                               return localOrderPostUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderPostUrl
                               */
                               public void setOrderPostUrl(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localOrderPostUrlTracker = true;
                                       } else {
                                          localOrderPostUrlTracker = false;
                                              
                                       }
                                   
                                            this.localOrderPostUrl=param;
                                    

                               }
                            

                        /**
                        * field for InterfaceName
                        */

                        
                                    protected java.lang.String localInterfaceName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInterfaceNameTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInterfaceName(){
                               return localInterfaceName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InterfaceName
                               */
                               public void setInterfaceName(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localInterfaceNameTracker = true;
                                       } else {
                                          localInterfaceNameTracker = false;
                                              
                                       }
                                   
                                            this.localInterfaceName=param;
                                    

                               }
                            

                        /**
                        * field for InterfaceVersion
                        */

                        
                                    protected java.lang.String localInterfaceVersion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInterfaceVersionTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInterfaceVersion(){
                               return localInterfaceVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InterfaceVersion
                               */
                               public void setInterfaceVersion(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localInterfaceVersionTracker = true;
                                       } else {
                                          localInterfaceVersionTracker = false;
                                              
                                       }
                                   
                                            this.localInterfaceVersion=param;
                                    

                               }
                            

                        /**
                        * field for Orderid
                        */

                        
                                    protected java.lang.String localOrderid ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderidTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderid(){
                               return localOrderid;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Orderid
                               */
                               public void setOrderid(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localOrderidTracker = true;
                                       } else {
                                          localOrderidTracker = false;
                                              
                                       }
                                   
                                            this.localOrderid=param;
                                    

                               }
                            

                        /**
                        * field for Amount
                        */

                        
                                    protected java.lang.String localAmount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAmountTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAmount(){
                               return localAmount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Amount
                               */
                               public void setAmount(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localAmountTracker = true;
                                       } else {
                                          localAmountTracker = false;
                                              
                                       }
                                   
                                            this.localAmount=param;
                                    

                               }
                            

                        /**
                        * field for CurType
                        */

                        
                                    protected java.lang.String localCurType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurTypeTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCurType(){
                               return localCurType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CurType
                               */
                               public void setCurType(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCurTypeTracker = true;
                                       } else {
                                          localCurTypeTracker = false;
                                              
                                       }
                                   
                                            this.localCurType=param;
                                    

                               }
                            

                        /**
                        * field for MerID
                        */

                        
                                    protected java.lang.String localMerID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerIDTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerID(){
                               return localMerID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerID
                               */
                               public void setMerID(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerIDTracker = true;
                                       } else {
                                          localMerIDTracker = false;
                                              
                                       }
                                   
                                            this.localMerID=param;
                                    

                               }
                            

                        /**
                        * field for MerAcct
                        */

                        
                                    protected java.lang.String localMerAcct ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerAcctTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerAcct(){
                               return localMerAcct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerAcct
                               */
                               public void setMerAcct(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerAcctTracker = true;
                                       } else {
                                          localMerAcctTracker = false;
                                              
                                       }
                                   
                                            this.localMerAcct=param;
                                    

                               }
                            

                        /**
                        * field for VerifyJoinFlag
                        */

                        
                                    protected java.lang.String localVerifyJoinFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVerifyJoinFlagTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVerifyJoinFlag(){
                               return localVerifyJoinFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VerifyJoinFlag
                               */
                               public void setVerifyJoinFlag(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localVerifyJoinFlagTracker = true;
                                       } else {
                                          localVerifyJoinFlagTracker = false;
                                              
                                       }
                                   
                                            this.localVerifyJoinFlag=param;
                                    

                               }
                            

                        /**
                        * field for NotifyType
                        */

                        
                                    protected java.lang.String localNotifyType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNotifyTypeTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNotifyType(){
                               return localNotifyType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NotifyType
                               */
                               public void setNotifyType(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localNotifyTypeTracker = true;
                                       } else {
                                          localNotifyTypeTracker = false;
                                              
                                       }
                                   
                                            this.localNotifyType=param;
                                    

                               }
                            

                        /**
                        * field for MerURL
                        */

                        
                                    protected java.lang.String localMerURL ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerURLTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerURL(){
                               return localMerURL;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerURL
                               */
                               public void setMerURL(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerURLTracker = true;
                                       } else {
                                          localMerURLTracker = false;
                                              
                                       }
                                   
                                            this.localMerURL=param;
                                    

                               }
                            

                        /**
                        * field for ResultType
                        */

                        
                                    protected java.lang.String localResultType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultTypeTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResultType(){
                               return localResultType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResultType
                               */
                               public void setResultType(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localResultTypeTracker = true;
                                       } else {
                                          localResultTypeTracker = false;
                                              
                                       }
                                   
                                            this.localResultType=param;
                                    

                               }
                            

                        /**
                        * field for OrderDate
                        */

                        
                                    protected java.lang.String localOrderDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOrderDateTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOrderDate(){
                               return localOrderDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OrderDate
                               */
                               public void setOrderDate(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localOrderDateTracker = true;
                                       } else {
                                          localOrderDateTracker = false;
                                              
                                       }
                                   
                                            this.localOrderDate=param;
                                    

                               }
                            

                        /**
                        * field for MerSignMsg
                        */

                        
                                    protected java.lang.String localMerSignMsg ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerSignMsgTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerSignMsg(){
                               return localMerSignMsg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerSignMsg
                               */
                               public void setMerSignMsg(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerSignMsgTracker = true;
                                       } else {
                                          localMerSignMsgTracker = false;
                                              
                                       }
                                   
                                            this.localMerSignMsg=param;
                                    

                               }
                            

                        /**
                        * field for MerCert
                        */

                        
                                    protected java.lang.String localMerCert ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerCertTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerCert(){
                               return localMerCert;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerCert
                               */
                               public void setMerCert(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerCertTracker = true;
                                       } else {
                                          localMerCertTracker = false;
                                              
                                       }
                                   
                                            this.localMerCert=param;
                                    

                               }
                            

                        /**
                        * field for GoodsID
                        */

                        
                                    protected java.lang.String localGoodsID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGoodsIDTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGoodsID(){
                               return localGoodsID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GoodsID
                               */
                               public void setGoodsID(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localGoodsIDTracker = true;
                                       } else {
                                          localGoodsIDTracker = false;
                                              
                                       }
                                   
                                            this.localGoodsID=param;
                                    

                               }
                            

                        /**
                        * field for GoodsName
                        */

                        
                                    protected java.lang.String localGoodsName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGoodsNameTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGoodsName(){
                               return localGoodsName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GoodsName
                               */
                               public void setGoodsName(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localGoodsNameTracker = true;
                                       } else {
                                          localGoodsNameTracker = false;
                                              
                                       }
                                   
                                            this.localGoodsName=param;
                                    

                               }
                            

                        /**
                        * field for GoodsNum
                        */

                        
                                    protected java.lang.String localGoodsNum ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGoodsNumTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGoodsNum(){
                               return localGoodsNum;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GoodsNum
                               */
                               public void setGoodsNum(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localGoodsNumTracker = true;
                                       } else {
                                          localGoodsNumTracker = false;
                                              
                                       }
                                   
                                            this.localGoodsNum=param;
                                    

                               }
                            

                        /**
                        * field for CarriageAmt
                        */

                        
                                    protected java.lang.String localCarriageAmt ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCarriageAmtTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCarriageAmt(){
                               return localCarriageAmt;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CarriageAmt
                               */
                               public void setCarriageAmt(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localCarriageAmtTracker = true;
                                       } else {
                                          localCarriageAmtTracker = false;
                                              
                                       }
                                   
                                            this.localCarriageAmt=param;
                                    

                               }
                            

                        /**
                        * field for Remark1
                        */

                        
                                    protected java.lang.String localRemark1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRemark1Tracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRemark1(){
                               return localRemark1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Remark1
                               */
                               public void setRemark1(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localRemark1Tracker = true;
                                       } else {
                                          localRemark1Tracker = false;
                                              
                                       }
                                   
                                            this.localRemark1=param;
                                    

                               }
                            

                        /**
                        * field for Remark2
                        */

                        
                                    protected java.lang.String localRemark2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRemark2Tracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRemark2(){
                               return localRemark2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Remark2
                               */
                               public void setRemark2(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localRemark2Tracker = true;
                                       } else {
                                          localRemark2Tracker = false;
                                              
                                       }
                                   
                                            this.localRemark2=param;
                                    

                               }
                            

                        /**
                        * field for MerHint
                        */

                        
                                    protected java.lang.String localMerHint ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerHintTracker = false ;
                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerHint(){
                               return localMerHint;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerHint
                               */
                               public void setMerHint(java.lang.String param){
                            
                                       if (param != null){
                                          //update the setting tracker
                                          localMerHintTracker = true;
                                       } else {
                                          localMerHintTracker = false;
                                              
                                       }
                                   
                                            this.localMerHint=param;
                                    

                               }
                            

     /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
   public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;
        
        try{
          isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        }catch(java.lang.IllegalArgumentException e){
          isReaderMTOMAware = false;
        }
        return isReaderMTOMAware;
   }
     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

                 public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
                       ICBC.this.serialize(parentQName,factory,xmlWriter);
                 }
               };
               return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(
               parentQName,factory,dataSource);
            
       }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       final org.apache.axiom.om.OMFactory factory,
                                       org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,factory,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               final org.apache.axiom.om.OMFactory factory,
                               org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();

                    if ((namespace != null) && (namespace.trim().length() > 0)) {
                        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
                        if (writerPrefix != null) {
                            xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
                        } else {
                            if (prefix == null) {
                                prefix = generatePrefix(namespace);
                            }

                            xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
                            xmlWriter.writeNamespace(prefix, namespace);
                            xmlWriter.setPrefix(prefix, namespace);
                        }
                    } else {
                        xmlWriter.writeStartElement(parentQName.getLocalPart());
                    }
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://pay.huat.edu.cn/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":ICBC",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "ICBC",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"IsCheck", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"IsCheck");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("IsCheck");
                                    }
                                
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("IsCheck cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsCheck));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localMerReferenceTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerReference", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerReference");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerReference");
                                    }
                                

                                          if (localMerReference==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerReference cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerReference);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTranDataTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"TranData", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"TranData");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("TranData");
                                    }
                                

                                          if (localTranData==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("TranData cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTranData);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderPostUrlTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"OrderPostUrl", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"OrderPostUrl");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("OrderPostUrl");
                                    }
                                

                                          if (localOrderPostUrl==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("OrderPostUrl cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderPostUrl);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInterfaceNameTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"InterfaceName", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"InterfaceName");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("InterfaceName");
                                    }
                                

                                          if (localInterfaceName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("InterfaceName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInterfaceName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInterfaceVersionTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"InterfaceVersion", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"InterfaceVersion");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("InterfaceVersion");
                                    }
                                

                                          if (localInterfaceVersion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("InterfaceVersion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInterfaceVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderidTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"Orderid", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"Orderid");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("Orderid");
                                    }
                                

                                          if (localOrderid==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Orderid cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderid);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAmountTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"Amount", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"Amount");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("Amount");
                                    }
                                

                                          if (localAmount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Amount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAmount);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCurTypeTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"CurType", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"CurType");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("CurType");
                                    }
                                

                                          if (localCurType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("CurType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCurType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerIDTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerID", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerID");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerID");
                                    }
                                

                                          if (localMerID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerAcctTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerAcct", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerAcct");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerAcct");
                                    }
                                

                                          if (localMerAcct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerAcct cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerAcct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVerifyJoinFlagTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"VerifyJoinFlag", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"VerifyJoinFlag");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("VerifyJoinFlag");
                                    }
                                

                                          if (localVerifyJoinFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("VerifyJoinFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVerifyJoinFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNotifyTypeTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"NotifyType", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"NotifyType");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("NotifyType");
                                    }
                                

                                          if (localNotifyType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("NotifyType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNotifyType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerURLTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerURL", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerURL");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerURL");
                                    }
                                

                                          if (localMerURL==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerURL cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerURL);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResultTypeTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"ResultType", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"ResultType");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("ResultType");
                                    }
                                

                                          if (localResultType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ResultType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResultType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOrderDateTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"OrderDate", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"OrderDate");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("OrderDate");
                                    }
                                

                                          if (localOrderDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("OrderDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOrderDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerSignMsgTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerSignMsg", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerSignMsg");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerSignMsg");
                                    }
                                

                                          if (localMerSignMsg==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerSignMsg cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerSignMsg);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerCertTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerCert", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerCert");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerCert");
                                    }
                                

                                          if (localMerCert==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerCert cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerCert);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGoodsIDTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"GoodsID", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"GoodsID");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("GoodsID");
                                    }
                                

                                          if (localGoodsID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("GoodsID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGoodsID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGoodsNameTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"GoodsName", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"GoodsName");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("GoodsName");
                                    }
                                

                                          if (localGoodsName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("GoodsName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGoodsName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGoodsNumTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"GoodsNum", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"GoodsNum");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("GoodsNum");
                                    }
                                

                                          if (localGoodsNum==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("GoodsNum cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGoodsNum);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCarriageAmtTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"CarriageAmt", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"CarriageAmt");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("CarriageAmt");
                                    }
                                

                                          if (localCarriageAmt==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("CarriageAmt cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCarriageAmt);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRemark1Tracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"Remark1", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"Remark1");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("Remark1");
                                    }
                                

                                          if (localRemark1==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Remark1 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRemark1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRemark2Tracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"Remark2", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"Remark2");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("Remark2");
                                    }
                                

                                          if (localRemark2==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Remark2 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRemark2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerHintTracker){
                                    namespace = "http://pay.huat.edu.cn/";
                                    if (! namespace.equals("")) {
                                        prefix = xmlWriter.getPrefix(namespace);

                                        if (prefix == null) {
                                            prefix = generatePrefix(namespace);

                                            xmlWriter.writeStartElement(prefix,"MerHint", namespace);
                                            xmlWriter.writeNamespace(prefix, namespace);
                                            xmlWriter.setPrefix(prefix, namespace);

                                        } else {
                                            xmlWriter.writeStartElement(namespace,"MerHint");
                                        }

                                    } else {
                                        xmlWriter.writeStartElement("MerHint");
                                    }
                                

                                          if (localMerHint==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MerHint cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerHint);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

         /**
          * Util method to write an attribute with the ns prefix
          */
          private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
              if (xmlWriter.getPrefix(namespace) == null) {
                       xmlWriter.writeNamespace(prefix, namespace);
                       xmlWriter.setPrefix(prefix, namespace);

              }

              xmlWriter.writeAttribute(namespace,attName,attValue);

         }

        /**
          * Util method to write an attribute without the ns prefix
          */
          private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                      java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
                if (namespace.equals(""))
              {
                  xmlWriter.writeAttribute(attName,attValue);
              }
              else
              {
                  registerPrefix(xmlWriter, namespace);
                  xmlWriter.writeAttribute(namespace,attName,attValue);
              }
          }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


         /**
         * Register a namespace prefix
         */
         private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
                java.lang.String prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                        prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                    }

                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                }

                return prefix;
            }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "IsCheck"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsCheck));
                             if (localMerReferenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerReference"));
                                 
                                        if (localMerReference != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerReference));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerReference cannot be null!!");
                                        }
                                    } if (localTranDataTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "TranData"));
                                 
                                        if (localTranData != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTranData));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("TranData cannot be null!!");
                                        }
                                    } if (localOrderPostUrlTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "OrderPostUrl"));
                                 
                                        if (localOrderPostUrl != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderPostUrl));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("OrderPostUrl cannot be null!!");
                                        }
                                    } if (localInterfaceNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "InterfaceName"));
                                 
                                        if (localInterfaceName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterfaceName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("InterfaceName cannot be null!!");
                                        }
                                    } if (localInterfaceVersionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "InterfaceVersion"));
                                 
                                        if (localInterfaceVersion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterfaceVersion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("InterfaceVersion cannot be null!!");
                                        }
                                    } if (localOrderidTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "Orderid"));
                                 
                                        if (localOrderid != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderid));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Orderid cannot be null!!");
                                        }
                                    } if (localAmountTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "Amount"));
                                 
                                        if (localAmount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Amount cannot be null!!");
                                        }
                                    } if (localCurTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "CurType"));
                                 
                                        if (localCurType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("CurType cannot be null!!");
                                        }
                                    } if (localMerIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerID"));
                                 
                                        if (localMerID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerID cannot be null!!");
                                        }
                                    } if (localMerAcctTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerAcct"));
                                 
                                        if (localMerAcct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerAcct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerAcct cannot be null!!");
                                        }
                                    } if (localVerifyJoinFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "VerifyJoinFlag"));
                                 
                                        if (localVerifyJoinFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVerifyJoinFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("VerifyJoinFlag cannot be null!!");
                                        }
                                    } if (localNotifyTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "NotifyType"));
                                 
                                        if (localNotifyType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNotifyType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("NotifyType cannot be null!!");
                                        }
                                    } if (localMerURLTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerURL"));
                                 
                                        if (localMerURL != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerURL));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerURL cannot be null!!");
                                        }
                                    } if (localResultTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "ResultType"));
                                 
                                        if (localResultType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ResultType cannot be null!!");
                                        }
                                    } if (localOrderDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "OrderDate"));
                                 
                                        if (localOrderDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrderDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("OrderDate cannot be null!!");
                                        }
                                    } if (localMerSignMsgTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerSignMsg"));
                                 
                                        if (localMerSignMsg != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerSignMsg));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerSignMsg cannot be null!!");
                                        }
                                    } if (localMerCertTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerCert"));
                                 
                                        if (localMerCert != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerCert));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerCert cannot be null!!");
                                        }
                                    } if (localGoodsIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "GoodsID"));
                                 
                                        if (localGoodsID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGoodsID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("GoodsID cannot be null!!");
                                        }
                                    } if (localGoodsNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "GoodsName"));
                                 
                                        if (localGoodsName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGoodsName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("GoodsName cannot be null!!");
                                        }
                                    } if (localGoodsNumTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "GoodsNum"));
                                 
                                        if (localGoodsNum != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGoodsNum));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("GoodsNum cannot be null!!");
                                        }
                                    } if (localCarriageAmtTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "CarriageAmt"));
                                 
                                        if (localCarriageAmt != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCarriageAmt));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("CarriageAmt cannot be null!!");
                                        }
                                    } if (localRemark1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "Remark1"));
                                 
                                        if (localRemark1 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRemark1));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Remark1 cannot be null!!");
                                        }
                                    } if (localRemark2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "Remark2"));
                                 
                                        if (localRemark2 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRemark2));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Remark2 cannot be null!!");
                                        }
                                    } if (localMerHintTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://pay.huat.edu.cn/",
                                                                      "MerHint"));
                                 
                                        if (localMerHint != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerHint));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MerHint cannot be null!!");
                                        }
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static ICBC parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ICBC object =
                new ICBC();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"ICBC".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ICBC)cn.edu.huat.pay.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                 
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","IsCheck").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIsCheck(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerReference").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerReference(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","TranData").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTranData(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","OrderPostUrl").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderPostUrl(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","InterfaceName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInterfaceName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","InterfaceVersion").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInterfaceVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","Orderid").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderid(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","Amount").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAmount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","CurType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCurType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerID").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerAcct").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerAcct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","VerifyJoinFlag").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVerifyJoinFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","NotifyType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNotifyType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerURL").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerURL(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","ResultType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResultType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","OrderDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOrderDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerSignMsg").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerSignMsg(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerCert").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerCert(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","GoodsID").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGoodsID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","GoodsName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGoodsName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","GoodsNum").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGoodsNum(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","CarriageAmt").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCarriageAmt(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","Remark1").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRemark1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","Remark2").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRemark2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://pay.huat.edu.cn/","MerHint").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerHint(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
          