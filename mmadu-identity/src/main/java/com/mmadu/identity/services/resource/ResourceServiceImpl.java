package com.mmadu.identity.services.resource;

import com.mmadu.identity.entities.Resource;
import com.mmadu.identity.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceServiceImpl implements ResourceService {
    private ResourceRepository resourceRepository;

    @Autowired
    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Optional<Resource> findByDomainIdAndIdentifier(String domainId, String identifier) {
        return resourceRepository.findByDomainIdAndIdentifier(domainId, identifier);
    }

    @Override
    public boolean existsByDomainIdAndIdentifier(String domainId, String identifier) {
        return resourceRepository.existsByDomainIdAndIdentifier(domainId, identifier);
    }

    @Override
    public boolean areAllResourcesSupportedInDomain(String domainId, List<String> resources) {
        Set<String> resourceSet = new HashSet<>(resources);
        return resourceRepository.countByDomainIdAndIdentifierIn(domainId, new ArrayList<>(resourceSet))
                == resourceSet.size();
    }
}
