package com.konifar.material_icon_generator;

import com.google.gson.Gson;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by family_lee on 2016/2/8.
 */
public class Configs {

    private static final String PATH_ICONS = "icons";
    private static final String PATH_DRAWABLE = "drawable";
    private static final String PATH_DRAWABLE_PREFIX = "drawable-";
    private static final String UNDERBAR = "_";
    private static final String PNG_SUFFIX = ".png";
    private static final String XML_SUFFIX = ".xml";
    private static final String WHITE = "white";

    private String dp="18dp";
    private boolean mdpi=true;
    private boolean hdpi=true;
    private boolean xhdpi=true;
    private boolean xxhdpi=true;
    private boolean xxxhdpi=true;
    private boolean autoGenXml=true;

    private IconInfo[] iconInfos;

    private String configFilePath;

    private String lastChooseIcon;

    private String lastIconTemplateName;

    private int scale=0;

    public Configs(){}

    public static Configs getConfigs(String configFilePath){
        Configs configs=null;
        String configStr=null;
        if(configFilePath!=null){
            File file=new File(configFilePath);
            if(file.exists()){
                try {
                    FileInputStream fileInputStream=new FileInputStream(file);
                    byte[] bytes=new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    configStr=new String(bytes);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(configStr!=null){
            Gson gson=new Gson();
            configs=gson.fromJson(configStr,Configs.class);
        }else{
            configs=new Configs();
            configs.iconInfos=new IconInfo[3];
            configs.iconInfos[0]=new IconInfo(true,"${name}_normal","#FFFFFF","none","true");
            configs.iconInfos[1]=new IconInfo(true,"${name}_pressed","#CCCCCC","state_pressed","true");
            configs.iconInfos[2]=new IconInfo(true,"${name}_disabled","#222222","state_enabled","false");
        }
        configs.setConfigFilePath(configFilePath);
        return configs;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getLastChooseIcon() {
        return lastChooseIcon;
    }

    public void setLastChooseIcon(String lastChooseIcon) {
        this.lastChooseIcon = lastChooseIcon;
    }

    public String getLastIconName(){
        if(lastChooseIcon!=null && lastChooseIcon.length()>0){
            String[] fileString = lastChooseIcon.split("/");
            if (fileString.length > 1){
                return fileString[1];
            }
        }
        return lastChooseIcon;
    }

    public String getLastIconTemplateName() {
        if(StringUtils.isEmpty(lastIconTemplateName)){
            return getLastIconName();
        }

        return lastIconTemplateName;
    }

    public void setLastIconTemplateName(String lastIconTemplateName) {
        this.lastIconTemplateName = lastIconTemplateName;
    }

    public String getDestFile(String resDir, String templateName, String name, String size) {
        StringBuilder sb = new StringBuilder();
        sb.append(resDir);
        sb.append(File.separator);
        sb.append(PATH_DRAWABLE_PREFIX);
        sb.append(size);
        sb.append(File.separator);
        sb.append(templateName.replace("${name}",name));
        sb.append(PNG_SUFFIX);
        return sb.toString();
    }

    public String getXmlFile(String resDir,String name){
        StringBuilder sb = new StringBuilder();
        sb.append(resDir);
        sb.append(File.separator);
        sb.append(PATH_DRAWABLE);
        sb.append(File.separator);
        sb.append(name);
        sb.append(XML_SUFFIX);
        return sb.toString();
    }

    public String getIconLocalPath(String size) {
        if (lastChooseIcon != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(PATH_ICONS);

            String[] fileString = lastChooseIcon.split("/");
            sb.append(getLocalDrawabaleIconPath(getIconName(fileString[1]), size));

            return sb.toString();
        } else {
            return "";
        }
    }

    private String getLocalDrawabaleIconPath(String fileName, String size) {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        String[] fileString = lastChooseIcon.split("/");
        sb.append(fileString[0]);
        sb.append("/");
        sb.append(PATH_DRAWABLE_PREFIX);
        sb.append(size);
        sb.append("/");
        sb.append(fileName);
        return sb.toString();
    }

    private String getIconName(String shortName) {
        return getIconName(shortName, WHITE);
    }

    private String getIconName(String shortName, String colorName) {
        StringBuilder sb = new StringBuilder();
        sb.append(shortName);
        sb.append(UNDERBAR);
        sb.append(colorName);
        sb.append(UNDERBAR);
        sb.append(dp);
        sb.append(PNG_SUFFIX);
        return sb.toString();
    }


    public boolean isAutoGenXml() {
        return autoGenXml;
    }

    public void setAutoGenXml(boolean autoGenXml) {
        this.autoGenXml = autoGenXml;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public void save(){
        if(configFilePath!=null){
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(configFilePath);
                String config=this.toString();
                fileOutputStream.write(config.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public boolean isMdpi() {
        return mdpi;
    }

    public void setMdpi(boolean mdpi) {
        this.mdpi = mdpi;
    }

    public boolean isHdpi() {
        return hdpi;
    }

    public void setHdpi(boolean hdpi) {
        this.hdpi = hdpi;
    }

    public boolean isXhdpi() {
        return xhdpi;
    }

    public void setXhdpi(boolean xhdpi) {
        this.xhdpi = xhdpi;
    }

    public boolean isXxhdpi() {
        return xxhdpi;
    }

    public void setXxhdpi(boolean xxhdpi) {
        this.xxhdpi = xxhdpi;
    }

    public boolean isXxxhdpi() {
        return xxxhdpi;
    }

    public void setXxxhdpi(boolean xxxhdpi) {
        this.xxxhdpi = xxxhdpi;
    }

    public IconInfo[] getIconInfos() {
        return iconInfos;
    }

    public void setIconInfos(IconInfo[] iconInfos) {
        this.iconInfos = iconInfos;
    }

    @Override
    public String toString() {
        Gson gson=new Gson();
        return gson.toJson(this,Configs.class);
    }

    public String generateResXml(String name){

        List<IconInfo> list= getStateEnabledIconInfos();
        if(list.size()<2){
            return null;
        }else{
            StringBuilder sb=new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
            sb.append("<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">\r\n");

            for(IconInfo iconInfo:list){
                if(iconInfo.isStateNone()){
                    sb.append(String.format("\t<item android:drawable=\"@drawable/%s\"/>\r\n",iconInfo.getName().replace("${name}",name)));
                }else{
                    sb.append((String.format("\t<item android:%s=\"%s\" android:drawable=\"@drawable/%s\" />\r\n",iconInfo.getState(),iconInfo.getStateValue(),iconInfo.getName().replace("${name}",name))));
                }
            }
            sb.append("</selector>");
            return sb.toString();
        }
    }

    private List<IconInfo> getStateEnabledIconInfos(){
        List<IconInfo> stateNoneIcons=new ArrayList<IconInfo>();
        List<IconInfo> stateOtherIcons=new ArrayList<IconInfo>();
        for(IconInfo iconInfo:iconInfos){
            if(iconInfo.isEnabled()){
                if(iconInfo.isStateNone()){
                    stateNoneIcons.add(iconInfo);
                }else{
                    stateOtherIcons.add(iconInfo);
                }
            }
        }
        stateOtherIcons.addAll(stateNoneIcons);
        return stateOtherIcons;
    }
}
