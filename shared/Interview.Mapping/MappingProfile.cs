using System.Linq;
using AutoMapper;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure;

namespace Interview.Mapping
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<QuestionDto, Question>()
                .ReverseMap();

            CreateMap<Question, QuestionSqlModel>()
                .ReverseMap();

            CreateMap<InterviewTemplateDto, InterviewTemplate>()
                .ForMember(d => d.Questions,
                    config => config.MapFrom(s =>
                        s.QuestionIds.Select(id =>
                            new Question
                            {
                                Id = id
                            })))
                .ReverseMap();
        }
    }
}