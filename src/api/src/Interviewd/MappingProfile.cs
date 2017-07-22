﻿using AutoMapper;
using Interviewd.Application.Dto;
using Interviewd.Domain;
using Interviewd.Infrastructure;

namespace Interviewd
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            CreateMap<QuestionDto, Question>()
                .ReverseMap();

            CreateMap<Question, QuestionSqlModel>()
                .ReverseMap();
        }
    }
}