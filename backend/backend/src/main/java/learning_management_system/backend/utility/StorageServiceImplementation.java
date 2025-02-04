package learning_management_system.backend.utility;

import learning_management_system.backend.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImplementation implements StorageService {

    @Autowired
    private AttachmentRepository attachmentRepository; // Assuming attachments store file metadata

    @Override
    public double getUsedStorage(Long tenantId) {
        // Fetch total file size for the tenant from attachments
        Long totalSizeInBytes = attachmentRepository.findTotalSizeByTenantId(tenantId);

        // Convert bytes to gigabytes (1 GB = 1,073,741,824 bytes)
        return totalSizeInBytes != null ? totalSizeInBytes / 1_073_741_824.0 : 0.0;
    }
}

