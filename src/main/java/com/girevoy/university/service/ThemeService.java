package com.girevoy.university.service;

import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.Theme;

public interface ThemeService extends Service<Theme> {
    void assignSubjectToTheme(Theme theme, Subject subject);
    void removeSubjectFromTheme(Theme theme);
}
