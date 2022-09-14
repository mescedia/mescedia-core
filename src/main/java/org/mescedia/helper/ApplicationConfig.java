package org.mescedia.helper;

import org.mescedia.analyser.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class ApplicationConfig {

    private List<MessageFormat> messageFormatList = new ArrayList<MessageFormat>()  ;

    private static ApplicationConfig instance = null ;
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    private ApplicationConfig()  {}

    public static ApplicationConfig getInstance() {

        if (instance == null ) {
            instance = new ApplicationConfig();
        }

        return instance ;
    }

    public void clear() {

        messageFormatList.clear();
    }

    public void addMessageFormat(MessageFormat _mf)  {

        this.messageFormatList.add(_mf);
    }

    public List<MessageFormat>  getMessageFormatList()   {

        return this.messageFormatList;
    }
}
