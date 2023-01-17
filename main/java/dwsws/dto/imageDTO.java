package dwsws.dto;

import org.springframework.web.multipart.MultipartFile;

public class imageDTO {
	private MultipartFile file;
	private String contentsName;
	
	public imageDTO(MultipartFile file, String contentsName) {
		this.file = file;
		this.contentsName = contentsName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getContentsName() {
		return contentsName;
	}

	public void setContentsName(String contentsName) {
		this.contentsName = contentsName;
	}

	@Override
	public String toString() {
		return "imageDTO [file=" + file + ", contentsName=" + contentsName + "]";
	}


	
}
