package com.epam.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "song")
public class SongEntity {

    @Id
    private Integer id;

    private String name;

    private String artist;

    private String album;

    private String duration;

    private String year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SongEntity entity = (SongEntity) o;
        return Objects.equals(id, entity.id)
                && Objects.equals(name, entity.name)
                && Objects.equals(artist, entity.artist)
                && Objects.equals(album, entity.album)
                && Objects.equals(duration, entity.duration)
                && Objects.equals(year, entity.year);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(artist);
        result = 31 * result + Objects.hashCode(album);
        result = 31 * result + Objects.hashCode(duration);
        result = 31 * result + Objects.hashCode(year);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SongEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("artist='" + artist + "'")
                .add("album='" + album + "'")
                .add("duration='" + duration + "'")
                .add("year='" + year + "'")
                .toString();
    }
}
