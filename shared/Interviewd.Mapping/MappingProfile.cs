using System.Linq;
using AutoMapper;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure;

namespace Interviewd.Mapping
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
                .ForMember(o => o.Questions,
                    config => config.MapFrom(s =>
                        s.QuestionIds.Select(id =>
                            new Question
                            {
                                Id = id
                            })))
                .ReverseMap()
                .ForMember(o => o.QuestionIds, config => 
                    config.MapFrom(o => o.Questions.Select(q => q.Id)));

            CreateMap<InterviewTemplateSqlModel, InterviewTemplate>()
                .ForMember(o => o.Name, config => config.MapFrom(o => o.Name));

            CreateMap<CandidateDto, Candidate>()
                .ReverseMap();

            CreateMap<CandidateSqlModel, Candidate>()
                .ReverseMap();

            CreateMap<Interview, InterviewDto>()
                .ReverseMap();

            CreateMap<InterviewSqlModel, Interview>()
                .ForMember(o => o.Candidate, config => config.ResolveUsing(o => 
                    new Candidate
                    {
                        Id = o.CandidateId,
                        GivenName = o.CandidateGivenName,
                        Surname = o.CandidateSurname
                    }
                ))
                .ReverseMap();
        }
    }
}