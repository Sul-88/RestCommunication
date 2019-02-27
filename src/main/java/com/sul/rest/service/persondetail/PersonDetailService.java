package com.sul.rest.service.persondetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sul.rest.service.dto.PersonDetailDTO;
import com.sul.rest.service.repository.PersonDetailJpaRepository;

/**
 * @author Sulaiman Abboud
 */
@Service
public class PersonDetailService implements UserDetailsService {
	private static final Logger log = LogManager.getLogger(PersonDetailService.class);

	@Autowired
	private PersonDetailJpaRepository personDetailJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String personName) throws UsernameNotFoundException {
		PersonDetailDTO personDetail = personDetailJpaRepository.findByUsername(personName);
		if (personDetail == null) {
			throw new UsernameNotFoundException("Person not found with name: " + personName);
		}
		log.debug("PersonDetails for name {} has been found", personName);
		return new org.springframework.security.core.userdetails.User(personDetail.getUsername(),
				personDetail.getPassword(), getAuthorities(personDetail));
	}

	private Collection<GrantedAuthority> getAuthorities(PersonDetailDTO personDetail) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities = AuthorityUtils.createAuthorityList(personDetail.getRole());
		return authorities;
	}

}
