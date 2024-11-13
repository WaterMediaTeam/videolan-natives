package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Union;

/**
 * Native event data.
 */
public class libvlc_event_u extends Union {

    public media_meta_changed               media_meta_changed;
    public media_subitem_added              media_subitem_added;
    public media_duration_changed           media_duration_changed;
    public media_parsed_changed             media_parsed_changed;
    public media_freed                      media_freed;
    public media_state_changed              media_state_changed;
    public media_thumbnail_generated        media_thumbnail_generated;
    public media_player_buffering           media_player_buffering;
    public media_player_position_changed    media_player_position_changed;
    public media_player_time_changed        media_player_time_changed;
    public media_player_title_changed       media_player_title_changed;
    public media_player_seekable_changed    media_player_seekable_changed;
    public media_player_pausable_changed    media_player_pausable_changed;
    public media_player_vout                media_player_vout;
    public media_list_item_added            media_list_item_added;
    public media_list_will_add_item         media_list_will_add_item;
    public media_list_item_deleted          media_list_item_deleted;
    public media_list_will_delete_item      media_list_will_delete_item;
    public media_list_player_next_item_set  media_list_player_next_item_set;
    public media_player_snapshot_taken      media_player_snapshot_taken;
    public media_player_length_changed      media_player_length_changed;
    public vlm_media_event                  vlm_media_event;
    public media_player_media_changed       media_player_media_changed;
    public media_player_scrambled_changed   media_player_scrambled_changed;
    public media_player_es_changed          media_player_es_changed;
    public media_subitemtree_added          media_subitemtree_added;
    public media_player_audio_volume        media_player_audio_volume;
    public media_player_audio_device        media_player_audio_device;
    public media_player_chapter_changed     media_player_chapter_changed;
    public renderer_discoverer_item_added   renderer_discoverer_item_added;
    public renderer_discoverer_item_deleted renderer_discoverer_item_deleted;
}