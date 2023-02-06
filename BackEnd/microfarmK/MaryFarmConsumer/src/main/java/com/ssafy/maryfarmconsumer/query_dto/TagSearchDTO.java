package com.ssafy.maryfarmconsumer.query_dto;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class TagSearchDTO {
//    private PlantResponseDTO plant;
//    private String content;
//    private Integer likes;
//    private String imagepath;
//    private List<String> taglist = new ArrayList<>();
//    private List<GroupedDiaryForSearchDTO> diarygroup = new ArrayList<>();
//
//    public static TagSearchDTO of(Diary diary, UserResponseDTO userDto, List<Diary> diarygroup) {
//        TagSearchDTO dto = new TagSearchDTO();
//        dto.plant = PlantResponseDTO.of(diary.getPlant(),userDto);
//        dto.content = diary.getContent();
//        dto.likes = diary.getLikes();
//        dto.imagepath = diary.getImagePath();
//        for(Tag t : diary.getTags()) {
//            dto.taglist.add(t.getName());
//        }
//        for(Diary d : diarygroup) {
//            dto.diarygroup.add(GroupedDiaryForSearchDTO.of(d));
//        }
//        return dto;
//    }
//}
