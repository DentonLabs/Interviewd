using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Domain
{
    public class InterviewManager : IInterviewManager
    {
        private readonly IMapper _Mapper;

        private readonly IInterviewRepository _InterviewRepository;

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        private readonly ICandidateRepository _CandidateRepository;

        public InterviewManager(
            IMapper mapper,
            IInterviewRepository interviewRepository,
            IInterviewTemplateRepository interviewTemplateRepository,
            ICandidateRepository candidateRepository)
        {
            _Mapper = mapper;
            _InterviewRepository = interviewRepository;
            _InterviewTemplateRepository = interviewTemplateRepository;
            _CandidateRepository = candidateRepository;
        }

        public async Task<InterviewDto> GetInterview(string id)
        {
            var interviewQuestions = await _InterviewRepository.GetInterviewQuestions(id);

            var interview = new Interview
            {
                Id = id,
                Questions = interviewQuestions
            };

            return _Mapper.Map<InterviewDto>(interview);
        }

        public async Task<IEnumerable<InterviewDto>> GetInterviews()
        {
            var interviews = await _InterviewRepository.GetInterviews();
            return _Mapper.Map<IEnumerable<InterviewDto>>(interviews);
        }

        public async Task<InterviewDto> CreateInterview(string templateId = null, string candidateId = null)
        {
            var createdInterview = await _InterviewRepository.InsertInterview();

            if (!string.IsNullOrWhiteSpace(candidateId))
            {
                var candidate = await _CandidateRepository.GetCandidate(candidateId);
                createdInterview.Candidate = candidate;
            }

            if (!string.IsNullOrWhiteSpace(templateId))
            {
                var interviewTemplate = await _InterviewTemplateRepository.GetInterviewTemplate(templateId);

                await _InterviewRepository.InsertInterviewQuestions(
                    createdInterview.Id,
                    interviewTemplate.Questions.Select(q => q.Id));

                createdInterview.Questions = interviewTemplate.Questions;
            }

            return _Mapper.Map<InterviewDto>(createdInterview);
        }
    }
}