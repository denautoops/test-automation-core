package core.db.encryption;

import org.hibernate.event.spi.*;

public class EncryptionListener implements PreInsertEventListener, PreUpdateEventListener, PreLoadEventListener {
    private final FieldEncrypter fieldEncrypter;
    private final FieldDecrypter fieldDecrypter;

    public EncryptionListener() {
        this.fieldEncrypter = new FieldEncrypter();
        this.fieldDecrypter = new FieldDecrypter();
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();
        Object entity = event.getEntity();
        fieldEncrypter.encrypt(state, propertyNames, entity);
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();
        Object entity = event.getEntity();
        fieldEncrypter.encrypt(state, propertyNames, entity);
        return false;
    }

    @Override
    public void onPreLoad(PreLoadEvent event) {
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();
        Object entity = event.getEntity();
        fieldDecrypter.decrypt(state, propertyNames, entity);
    }
}
