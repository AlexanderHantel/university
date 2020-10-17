package com.girevoy.university.service.impl;

import com.girevoy.university.dao.ThemeDAO;
import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.Theme;
import com.girevoy.university.service.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl extends AbstractServiceImpl<Theme> implements ThemeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThemeServiceImpl.class);

    public ThemeServiceImpl(ThemeDAO themeDAO) {
        super(themeDAO);
    }

    @Override
    public void assignSubjectToTheme(Theme theme, Subject subject) {
        theme.setSubject(subject);
        update(theme);
    }

    @Override
    public void removeSubjectFromTheme(Theme theme) {
        theme.setSubject(new Subject());
        update(theme);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Theme";
    }

    @Override
    public Theme findByID(long id) {
        return getDao().findByID(id).orElse(new Theme());
    }
}
