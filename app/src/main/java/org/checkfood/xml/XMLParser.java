package org.checkfood.xml;


import java.io.IOException;

import java.io.StringReader;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import org.checkfood.model.Additive;
import org.checkfood.model.Product;

public class XMLParser {
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_NAME = "name";
    private static final String TAG_DANGER = "danger";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_LINK = "link";
    private static final String TAG_ADDITIVE = "additive";
    private static final String TAG_NUMBER = "number";
    private static final String TAG_NOT_ALLOWED_EU = "notAllowedEU";
    private static final String TAG_NOT_ALLOWED_RU = "notAllowedRU";
    private static final String TAG_NOT_FOUND = "notFound";

    public static Product parseProduct(String xml) throws XmlPullParserException, IOException {
        Product product = null;
        Product result = null;
        Additive additive = null;

        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(true);
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(xml));

        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equalsIgnoreCase(TAG_NOT_FOUND)) {
                        product = new Product();
                        product.setNotFound(Boolean.valueOf(Integer.parseInt(xmlPullParser.nextText()) == 1 ? true : false));
                        return product;
                    }

                    if (xmlPullParser.getName().equalsIgnoreCase(TAG_PRODUCT)) {

                        product = new Product();
                        break;
                    }

                    if (product != null && xmlPullParser.getName().equalsIgnoreCase(TAG_NAME)) {
                        product.setName(xmlPullParser.nextText());
                        break;
                    }

                    if (product != null && xmlPullParser.getName().equalsIgnoreCase(TAG_DANGER)) {
                        product.setDanger(Integer.parseInt(xmlPullParser.nextText()));

                        break;
                    }

                    if (product != null && xmlPullParser.getName().equalsIgnoreCase(TAG_IMAGE)) {
                        product.setImageLink(xmlPullParser.nextText());
                        break;
                    }

                    if (product != null && xmlPullParser.getName().equalsIgnoreCase(TAG_LINK)) {
                        product.setLink((xmlPullParser.nextText()));
                        break;
                    }

                    if (xmlPullParser.getName().equalsIgnoreCase(TAG_ADDITIVE)) {
                        additive = new Additive();
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_NUMBER)) {
                        additive.setNumber(xmlPullParser.nextText());
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_NAME)) {
                        additive.setName(xmlPullParser.nextText());
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_DANGER)) {
                        additive.setDanger(Integer.parseInt(xmlPullParser.nextText()));
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_LINK)) {
                        additive.setLink(xmlPullParser.nextText());
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_NOT_ALLOWED_EU)) {
                        additive.setNotAllowedEU(Boolean.valueOf(Integer.parseInt(xmlPullParser.nextText()) == 1 ? true
                                : false));
                        break;
                    }

                    if (additive != null && xmlPullParser.getName().equalsIgnoreCase(TAG_NOT_ALLOWED_RU)) {
                        additive.setNotAllowedRU(Boolean.valueOf(Integer.parseInt(xmlPullParser.nextText()) == 1 ? true
                                : false));
                        break;
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xmlPullParser.getName().equalsIgnoreCase(TAG_PRODUCT)) {
                        result=product;
                        product=null;
                        break;
                    }

                    if (xmlPullParser.getName().equalsIgnoreCase(TAG_ADDITIVE)) {
                        result.getAdditiveList().add(additive);
                        additive=null;
                        break;
                    }

                    break;
            }
            eventType = xmlPullParser.next();
        }

        return result;
    }




}
