
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:19:26 CET)
 */

        
            package cn.edu.huat.pay;
        
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://pay.huat.edu.cn/".equals(namespaceURI) &&
                  "GetOrderInfoResult_type0".equals(typeName)){
                   
                            return  cn.edu.huat.pay.GetOrderInfoResult_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://pay.huat.edu.cn/".equals(namespaceURI) &&
                  "ICBC".equals(typeName)){
                   
                            return  cn.edu.huat.pay.ICBC.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    