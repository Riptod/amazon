package mate.amazon.service.impl;

import mate.amazon.entity.Role;
import mate.amazon.repository.RoleRepository;
import mate.amazon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
