package br.com.personal_financee.pf.models;

import br.com.personal_financee.pf.models.Launches;

import javax.persistence.*;

@Entity
public class LaunchesAtach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_launchesAtach;

    private byte[] atach;

    @ManyToOne
    @JoinColumn(name = "id_launch", referencedColumnName = "id_launch")
    private Launches launches;

    private String extension;

    private String fileName;

    public Long getId_launchesAtach() {
        return id_launchesAtach;
    }

    public void setId_launchesAtach(Long id_launchesAtach) {
        this.id_launchesAtach = id_launchesAtach;
    }

    public byte[] getAtach() {
        return atach;
    }

    public void setAtach(byte[] atach) {
        this.atach = atach;
    }

    public Launches getLaunches() {
        return launches;
    }

    public void setLaunches(Launches launches) {
        this.launches = launches;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
