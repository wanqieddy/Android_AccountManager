package com.phicomm.account.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class ReadDoc {
    private static final String TAG = "ReadDoc";

    public static Result readXML(Document inStream) {
        Result result = new Result();
        try {
            Element root = inStream.getDocumentElement();
            NodeList items = root.getChildNodes();
            for (int j = 0; j < items.getLength(); j++) {
                Node node = items.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element childNode = (Element) node;
                    if ("resultCode".equals(childNode.getNodeName())) {
                        result.setStatusCode(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("message".equals(childNode.getNodeName())) {
                        result.setResultMsg(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("user_key".equals(childNode.getNodeName())) {
                        result.setKeyuserKey(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userImage".equals(childNode.getNodeName())) {
                        result.setImage(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userNickName".equals(childNode.getNodeName())) {
                        result.setNickName(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userId".equals(childNode.getNodeName())) {
                        result.setUserId(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userName".equals(childNode.getNodeName())) {
                        result.setUserName(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userTrueName".equals(childNode.getNodeName())) {
                        result.setUserTrueName(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userSex".equals(childNode.getNodeName())) {
                        result.setUserSex(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userBirth".equals(childNode.getNodeName())) {
                        result.setUserBirth(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userAddress".equals(childNode.getNodeName())) {
                        result.setUserAddress(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userEmail".equals(childNode.getNodeName())) {
                        result.setUserEmail(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userCreateTime".equals(childNode.getNodeName())) {
                        result.setUserCreateTime(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userPhone".equals(childNode.getNodeName())) {
                        result.setPhone(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userAge".equals(childNode.getNodeName())) {
                        result.setUserAge(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("userPassword".equals(childNode.getNodeName())) {
                        result.setPassword(childNode.getFirstChild()
                                .getNodeValue());
                    } else if ("jssessionid".equals(childNode.getNodeName())) {
                        result.setJssessionid(childNode.getFirstChild()
                                .getNodeValue());
                    }
                }
            }
            Log.e(TAG, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Contact> readXMLToContact(Document inStream) {
        ArrayList<Contact> contactListArr = new ArrayList<Contact>();
        Contact contact = null;
        try {
            Element root = inStream.getDocumentElement();
            NodeList items = root.getChildNodes();
            contact = new Contact();
            for (int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                if (node.getNodeName().equals("resultCode")) {
                    //<nodes><resultCode>1</resultCode><message>user_key error !</message></nodes>
                    String resultCode = node.getFirstChild().getNodeValue();
                    if(resultCode.equals("1")){
                        return contactListArr;
                    }
                    continue;
                }
                if (node.getNodeName().equals("sync_time")) {
                    String syncTime = node.getFirstChild().getNodeValue();
                    contact.setSyncTime(syncTime);
                    continue;
                }
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childList = node.getChildNodes();
                    for (int j = 0; j < childList.getLength(); j++) {
                        Node childNode = childList.item(j);

                        if ("contact_id".equals(childNode.getNodeName())) {
                            contact.setContactId(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("change_time"
                                .equals(childNode.getNodeName())) {
                            contact.setChangeTime(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("operate_id".equals(childNode.getNodeName())) {
                            contact.setOperateId(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("display_name".equals(childNode
                                .getNodeName())) {
                            contact.setDisplayName(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("nick_name".equals(childNode.getNodeName())) {
                            contact.setNickName(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("work_address".equals(childNode
                                .getNodeName())) {
                            contact.setWorkAddress(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("home_address".equals(childNode
                                .getNodeName())) {
                            contact.setHomeAddress(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("organization".equals(childNode
                                .getNodeName())) {
                            contact.setOrganization(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("mobile_phone".equals(childNode
                                .getNodeName())) {
                            contact.setMobilePhone(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("telephone".equals(childNode.getNodeName())) {
                            contact.setTelephone(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("workphone".equals(childNode.getNodeName())) {
                            contact.setWorkphone(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("email".equals(childNode.getNodeName())) {
                            contact.setEmail(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("website".equals(childNode.getNodeName())) {
                            contact.setWebsite(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("photo_img".equals(childNode.getNodeName())) {
                            contact.setPhotoImg(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("note".equals(childNode.getNodeName())) {
                            contact.setNote(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("im".equals(childNode.getNodeName())) {
                            contact.setIm(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("service_contact_id".equals(childNode.getNodeName())) {
                            contact.setGlobalId(childNode.getFirstChild()
                                    .getNodeValue());
                        } else if ("group_member".equals(childNode.getNodeName())) {
                            if(isObjectNull(childNode.getFirstChild())){
                                contact.setGroupMember("");
                            }else{
                                contact.setGroupMember(childNode.getFirstChild().getNodeValue());
                            }
                        }
                    }
                    contactListArr.add(contact);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactListArr;
    }
    public static ArrayList<MapId> readXMLToMapList(Document inStream) {
        ArrayList<MapId> contactListArr = new ArrayList<MapId>();
        MapId map = null;
        try {
            Element root = inStream.getDocumentElement();
            NodeList items = root.getChildNodes();
            map = new MapId();
            for (int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                if (node.getNodeName().equals("resultCode")) {
                    //<nodes><resultCode>1</resultCode><message>user_key error !</message></nodes>
                    String resultCode = node.getFirstChild().getNodeValue();
                    if(resultCode.equals("1")){
                        return contactListArr;
                    }
                    continue;
                }
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childList = node.getChildNodes();
                    for (int j = 0; j < childList.getLength(); j++) {
                        Node childNode = childList.item(j);

                        if ("mobile_contact_id".equals(childNode.getNodeName())) {
                            map.setMobileContactId(childNode.getFirstChild().getNodeValue());
                        } else if ("service_contact_id"
                                .equals(childNode.getNodeName())) {
                            map.setServiceContactId(childNode.getFirstChild().getNodeValue());
                        }
                    }
                    contactListArr.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactListArr;
    }
    public static boolean isObjectNull(Object object){
        if(object == null){
            return true;
        }else{
            return false;
        }
    }

}