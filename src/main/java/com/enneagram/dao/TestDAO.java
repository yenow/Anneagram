package com.enneagram.dao;

import com.enneagram.vo.PersonalityVO;

public interface TestDAO {

	void insertPersonality(PersonalityVO p);

	String getEclass(int eclass);

	String getType(Integer type);

}
